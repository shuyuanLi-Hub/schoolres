package org.shuyuan.schoolres.service.address.impl;

import org.shuyuan.schoolres.dao.AddressDao;
import org.shuyuan.schoolres.domain.Address;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.address.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService
{
    private AddressDao dao;

    public AddressServiceImpl(AddressDao dao)
    {
        this.dao = dao;
    }

    @Override
    public void save(User user, String detail)
    {
        if (dao.findAddressByDetailAndUserId(detail, user.getId()) == null)
        {
            var address = new Address();
            address.setUser(user);
            address.setDetail(detail);
            dao.save(address);
        }
    }
}
