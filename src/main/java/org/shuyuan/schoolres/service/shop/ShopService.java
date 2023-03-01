package org.shuyuan.schoolres.service.shop;

import org.shuyuan.schoolres.domain.Shop;

public interface ShopService
{
    Shop findShopByName(String name);

    Shop findShopByAccountAndPasswd(String account, String passwd);

    Shop findShopByNameAndCategory(String name, Integer category);

    Boolean login(String name, String passwd);
}
