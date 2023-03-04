package org.shuyuan.schoolres.service.user;

import org.shuyuan.schoolres.domain.User;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.List;

public interface UserService
{
    void save(User user);

    User findUserByName(String name);

    void deleteUserByName(String name);

    BufferedImage createVerifyCode(String token);

    List<String> findUserAddress(String name);

    Boolean checkVerifyCode(String token, String code);

    User getByName(String name);

    User login(User user);

}
