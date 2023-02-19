package org.shuyuan.schoolres.service.orderDishes.orderDishesServiceImpl;

import org.shuyuan.schoolres.dao.OrderDishesDao;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.service.orderDishes.OrderDishesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDishesServiceImpl implements OrderDishesService
{
    private OrderDishesDao dao;

    public OrderDishesServiceImpl(OrderDishesDao dao)
    {
        this.dao = dao;
    }

    @Override
    public List<Order.OrderDishes> findOrderDishesByShop(Integer id)
    {
        return dao.findOrderDishesByShop(new Shop(id));
    }
}
