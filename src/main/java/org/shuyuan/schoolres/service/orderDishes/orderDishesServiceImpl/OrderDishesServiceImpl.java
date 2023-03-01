package org.shuyuan.schoolres.service.orderDishes.orderDishesServiceImpl;

import org.shuyuan.schoolres.dao.OrderDishesDao;
import org.shuyuan.schoolres.dao.ShopDao;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.service.orderDishes.OrderDishesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDishesServiceImpl implements OrderDishesService
{
    private OrderDishesDao dao;
    private ShopDao shopDao;

    public OrderDishesServiceImpl(OrderDishesDao dao, ShopDao shopDao)
    {
        this.dao = dao;
        this.shopDao = shopDao;
    }

    @Override
    public List<Order.OrderDishes> findOrderDishesByShop(String name, Integer category)
    {
        return dao.findOrderDishesByShop(shopDao.findShopByNameAndCategory(name, category));
    }
}
