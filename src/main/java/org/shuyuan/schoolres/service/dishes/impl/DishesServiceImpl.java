package org.shuyuan.schoolres.service.dishes.impl;

import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.dao.DishesDao;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.service.dishes.DishesService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DishesServiceImpl implements DishesService
{
    private DishesDao dao;
    private Integer count = 0;

    public DishesServiceImpl(DishesDao dao)
    {
        this.dao = dao;
    }

    @Override
    public void save(Dishes dishes)
    {
        dao.save(dishes);
    }

    @Override
    public List<Dishes> findAll()
    {
        return (List<Dishes>) dao.findAll();
    }

    @Override
    public List<Dishes> findAllByPage(Pageable pageable)
    {
        return dao.findAll(pageable).stream().toList();
    }

    @Override
    public Dishes findDishesByName(String name)
    {
        return dao.findDishesByName(name);
    }

    @Override
    public List<Dishes> findDishesByCategory(Integer category)
    {
        return null;
    }

    @Override
    public List<Dishes> findDishesByCategory(Integer category, Integer size)
    {
        List<Dishes> dishes = dao.findDishesByCategory(category, PageRequest.of(count, size));

        if (dishes.size() == 0)
        {
            count = 1;
            return dao.findDishesByCategory(category, PageRequest.of(0, size));
        }

        return dishes;
    }

    @Override
    public List<Dishes> findDishesByNameLikeOrShopLike(String pattern)
    {
         return dao.findDishesByNameLikeOrShopLike(pattern);
    }
}
