package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDishesDao extends CrudRepository<Order.OrderDishes, Integer>
{
    List<Order.OrderDishes> findOrderDishesByShop(Shop shop);

    Order.OrderDishes findOrderDishesById(Integer id);

//    List<Order.OrderDishes> findOrderDishesByStatusAndG
}
