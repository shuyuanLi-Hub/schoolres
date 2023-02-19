package org.shuyuan.schoolres.service.user;

import org.shuyuan.schoolres.domain.User;

import java.util.List;

public interface UserService
{
    void save(User user);
    User findUserByName(String name);
    void deleteUserByName(String name);

    List<String> findUserAddress(String name);
}
