package org.shuyuan.schoolres.service.orderDishes;

import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;

import java.util.List;

public interface OrderDishesService
{
    List<Order.OrderDishes> findOrderDishesByShop(String name, Integer category);
}
