package org.shuyuan.schoolres.service.user.impl;

import org.shuyuan.schoolres.dao.UserDao;
import org.shuyuan.schoolres.domain.Address;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao)
    {
        this.userDao = userDao;
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
}
