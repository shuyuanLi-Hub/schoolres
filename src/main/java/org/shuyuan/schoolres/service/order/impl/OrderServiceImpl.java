package org.shuyuan.schoolres.service.order.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.shuyuan.schoolres.dao.*;
import org.shuyuan.schoolres.domain.*;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.utils.CustomRedisUtil;
import org.shuyuan.schoolres.utils.RabbitMQUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class OrderServiceImpl implements OrderService
{
    private OrderDao dao;
    private OrderDishesDao dishesDao;
    private ShopDao shopDao;
    private RiderDao riderDao;
    private UserDao userDao;

    private final String EXCHANGE_NAME = "order";
    private final String QUEUE_NAME = "orders";

    public OrderServiceImpl(OrderDao dao, OrderDishesDao dishesDao, ShopDao shopDao, RiderDao riderDao, UserDao userDao)
    {
        this.dao = dao;
        this.dishesDao = dishesDao;
        this.shopDao = shopDao;
        this.riderDao = riderDao;
        this.userDao = userDao;
    }

    @Override
    public void orderOver(AmqpAdmin amqpAdmin, AmqpTemplate template, List<Map<String, String>> message) throws JsonProcessingException
    {
        RabbitMQUtil.declare(amqpAdmin, EXCHANGE_NAME, QUEUE_NAME);
        RabbitMQUtil.produce(template, EXCHANGE_NAME, CustomRedisUtil.beanToString(message));
    }

    @Transactional
    @Async
    @RabbitListener(queues = "orders")
    public void consumer(String message)
    {
        List<Map<String, String>> map = CustomRedisUtil.stringToBean(message, List.class);
        List<Map<String, String>> data = new ArrayList<>();
        int id = Integer.parseInt(map.get(map.size() - 2).get("user"));

        for (int i = 0; i < map.size() - 2; i++)
        {
            data.add(map.get(i));
        }

        Calendar calendar = Calendar.getInstance();

        Map<String, Map<Order, List<Order.OrderDishes>>> orderMap = new HashMap<>();

        data.forEach(e -> {
            if (orderMap.get(e.get("shop")) == null)
            {
                Map<Order, List<Order.OrderDishes>> od = new HashMap<>();
                Order order = new Order(calendar.getTime(),
                        new User(id),
                        '0',
//                        e.get("address"),
                        map.get(map.size() - 1).get("addr"),
                        map.get(map.size() - 1).get("remark"));

                List<Order.OrderDishes> orderDishes = new ArrayList<>();
                orderDishes.add(new Order.OrderDishes(
                        new Dishes(Integer.valueOf(e.get("id"))),
                        null ,
                        Integer.valueOf(e.get("count")),
                        new Shop(shopDao.findShopByName(e.get("shop")).getId())));

                od.put(order, orderDishes);

                orderMap.put(e.get("shop"), od);
            }
            else
            {
                Map<Order, List<Order.OrderDishes>> od = orderMap.get(e.get("shop"));
                od.forEach((k, v) -> {
                    v.add(new Order.OrderDishes(
                            new Dishes(Integer.valueOf(e.get("id"))),
                            null ,
                            Integer.valueOf(e.get("count")),
                            new Shop(shopDao.findShopByName(e.get("shop")).getId())));
                });
            }
        });

        orderMap.forEach((k, v) -> {
            v.forEach((i, j) -> {
                save(i, j);
            });
        });
    }

    @Override
    public void save(Order order, List<Order.OrderDishes> dishesList)
    {
        Order od = dao.save(order);
        
        dishesList.forEach(v -> {
            v.setStatus('0');
            v.setOrder(od);
            dishesDao.save(v);
        });
    }

    @Override
    public List<Order> findOrderByStatus(Character status)
    {
        return dao.findOrderByStatus(status);
    }

    @Override
    public List<Order> findOrderByRider(String name)
    {
//        Map<String, List<Order>> orders = new HashMap<>();
//        List<Order> errOrders = dao.findOrderByRiderAndStatus(riderDao.findRiderByName(name), '0');
//        List<Order> okOrders = dao.findOrderByRiderAndStatus(riderDao.findRiderByName(name), '1');
//
//        if (errOrders != null)
//        {
//            orders.put("err", errOrders);
//        }
//
//        if (okOrders != null)
//        {
//            orders.put("ok", okOrders);
//        }

        return dao.findOrderByRider(riderDao.findRiderByName(name));
    }

    @Override
    public void updateStatusAndRider(Character status, Integer rider, Integer id)
    {
        dao.updateStatusAndRider(status, new Rider(rider), id);
    }

    @Override
    public void updateStatusById(Character status, Integer id)
    {
        dao.updateStatusById(status, id);
    }

    @Override
    public List<Order> findOrderByRiderAndStatus(Rider rider, Character status)
    {
        return dao.findOrderByRiderAndStatus(rider, status);
    }

    @Override
    public List<Order> findOrderByUserAndStatus(String user, Character status)
    {
        return dao.findOrderByUserAndStatus(userDao.findUserByName(user), status);
    }

    @Override
    public List<Order> findOrderByUserAndStatusNotIn(String name, List<Character> statuses)
    {
        return dao.findOrderByUserAndStatusNotIn(userDao.findUserByName(name), statuses);
    }

    @Override
    public Order findOrderById(Integer id)
    {
        return dao.findById(id).get();
    }
}
