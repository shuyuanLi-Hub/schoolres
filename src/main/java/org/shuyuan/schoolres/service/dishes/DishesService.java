package org.shuyuan.schoolres.service.dishes;

import org.shuyuan.schoolres.domain.Dishes;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishesService
{
    void save(Dishes dishes);

    List<Dishes> findAll();

    List<Dishes> findAllByPage(Pageable pageable);

    Dishes findDishesByName(String name);

    List<Dishes> findDishesByCategory(Integer category);

    List<Dishes> findDishesByCategory(Integer category, Integer size);

    List<Dishes> findDishesByNameLikeOrShopLike(String pattern);
}
