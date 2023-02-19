package org.shuyuan.schoolres.service.shop.impl;

import org.shuyuan.schoolres.dao.ShopDao;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.service.shop.ShopService;
import org.springframework.stereotype.Service;

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
}
