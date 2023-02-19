package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Address;
import org.shuyuan.schoolres.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Integer>
{
    User findUserByName(String name);

    void  deleteUserByName(String name);

}
