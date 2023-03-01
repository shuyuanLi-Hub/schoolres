package org.shuyuan.schoolres.service.shop.impl;

import org.shuyuan.schoolres.dao.ShopDao;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.service.shop.ShopService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShopServiceImpl implements ShopService
{
    private ShopDao dao;

    public ShopServiceImpl(ShopDao dao)
    {
        this.dao = dao;
    }

    @Override
    public Shop findShopByName(String name)
    {
        return dao.findShopByName(name);
    }

    @Override
    public Shop findShopByAccountAndPasswd(String account, String passwd)
    {
        return dao.findShopByAccountAndPasswd(account, passwd);
    }

    @Override
    public Shop findShopByNameAndCategory(String name, Integer category)
    {
        return dao.findShopByNameAndCategory(name, category);
    }

    @Override
    public Boolean login(String name, String passwd)
    {
        if (Objects.nonNull(findShopByAccountAndPasswd(name, passwd)))
        {
            return true;
        }

        return false;
    }
}
