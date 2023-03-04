package org.shuyuan.schoolres.service.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.domain.User;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.List;
import java.util.Map;

public interface OrderService
{
    void save(Order order, List<Order.OrderDishes> dishesList);

    List<Order> findOrderByStatus(Character status);

    List<Order> findOrderByRider(String name);

    void updateStatusAndRider(Character status, Integer rider, Integer id);

    void updateStatusById(Character status, Integer id);

    List<Order> findOrderByRiderAndStatus(Rider rider, Character status);

    List<Order> findOrderByUserAndStatus(String user, Character status);

    List<Order> findOrderByUserAndStatusNotIn(String name, List<Character> statuses);

    Order findOrderById(Integer id);

    void orderOver(AmqpAdmin amqpAdmin, AmqpTemplate template, List<Map<String, String>> message) throws JsonProcessingException;
}
