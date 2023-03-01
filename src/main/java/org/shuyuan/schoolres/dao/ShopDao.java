package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShopDao extends CrudRepository<Shop, Integer>, PagingAndSortingRepository<Shop, Integer>
{
    Shop findShopByName(String name);

    Shop findShopByAccountAndPasswd(String account, String passwd);

    List<Shop> findShopByCategory(Integer category);

    Shop findShopByNameAndCategory(String name, Integer category);
}
