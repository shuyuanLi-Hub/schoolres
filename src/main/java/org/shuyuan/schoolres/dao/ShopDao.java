package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShopDao extends CrudRepository<Shop, Integer>, PagingAndSortingRepository<Shop, Integer>
{
    Shop findShopByName(String name);

    List<Shop> findShopByCategory(Integer category);
}
