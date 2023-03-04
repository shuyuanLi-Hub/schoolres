package org.shuyuan.schoolres.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.shuyuan.schoolres.dao.UserDao;
import org.shuyuan.schoolres.domain.Address;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.domain.redis.LoginU;
import org.shuyuan.schoolres.exceptions.CodeMsg;
import org.shuyuan.schoolres.exceptions.UserException;
import org.shuyuan.schoolres.service.user.UserService;
import org.shuyuan.schoolres.utils.CustomRedisUtil;
import org.shuyuan.schoolres.utils.UserKey;
import org.shuyuan.schoolres.utils.VercodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService
{
    private final UserDao userDao;
    private CustomRedisUtil redisUtil;

    public UserServiceImpl(UserDao userDao, CustomRedisUtil redisUtil)
    {
        this.userDao = userDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public void save(User user)
    {
        userDao.save(user);
    }

    @Override
    public User findUserByName(String name)
    {
        return userDao.findUserByName(name);
    }

    @Override
    public void deleteUserByName(String name)
    {
        userDao.deleteUserByName(name);
    }

    @Override
    public List<String> findUserAddress(String name)
    {
        List<Address> addresses = userDao.findUserByName(name).getAddresses();
        List<String> addr_str = new ArrayList<>();

        addresses.forEach(e -> {
            addr_str.add(e.getDetail());
        });

        return addr_str;
    }

    public BufferedImage createVerifyCode(String token)
    {
        if (token == null)
        {
            return null;
        }

        Random rdm = new Random();
        String verifyCode = VercodeUtil.getVerifyCode(rdm);

        redisUtil.set(UserKey.verifyCode, token, verifyCode);

        return VercodeUtil.getVerifyCodeImg(verifyCode, rdm);
    }

    public Boolean checkVerifyCode(String sessionId, String code)
    {
        if (code == null)
        {
            return false;
        }

        String verifyCode = redisUtil.get(UserKey.verifyCode, sessionId, String.class);

        if (verifyCode == null || !verifyCode.equals(code))
        {
            return false;
        }

        redisUtil.delete(UserKey.verifyCode, sessionId);
        return true;
    }

    public User getByName(String name)
    {
        User user = redisUtil.get(UserKey.user, name, User.class);

        if (user != null)
        {
            return user;
        }

        user = userDao.findUserByName(name);

        if (user != null)
        {
            redisUtil.set(UserKey.user, name, user);
        }

        return user;
    }

    @SneakyThrows
    @Transactional
    public User login(User u)
    {
        if (u == null)
        {
            throw new UserException(CodeMsg.SERVER_INNER_ERROR);
        }

        // 数据库中的user
        User user = getByName(u.getName());

        if (user == null)
        {
            throw new UserException(CodeMsg.USER_NOT_EXISTS);
        }

        if (!u.getPasswd().equals(user.getPasswd()))
        {
            throw new UserException(CodeMsg.PASSWD_ERROR);
        }

        return user;
    }
}
