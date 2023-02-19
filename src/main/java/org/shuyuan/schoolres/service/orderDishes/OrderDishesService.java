package org.shuyuan.schoolres.service.orderDishes;

import org.shuyuan.schoolres.domain.Order;

import java.util.List;

public interface OrderDishesService
{
    List<Order.OrderDishes> findOrderDishesByShop(Integer id);
}
