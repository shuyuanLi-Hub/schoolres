package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Dishes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DishesDao extends CrudRepository<Dishes, Integer>, PagingAndSortingRepository<Dishes, Integer>
{
    Dishes findDishesByName(String name);

    List<Dishes> findDishesByCategory(Integer category);

    List<Dishes> findDishesByCategory(Integer category, Pageable page);

    @Query("select dishes from Dishes dishes where dishes.name like %?1% or dishes.shop in (select shop.id from Shop shop where shop.name like %?1%)")
    List<Dishes> findDishesByNameLikeOrShopLike(String pattern);
}
