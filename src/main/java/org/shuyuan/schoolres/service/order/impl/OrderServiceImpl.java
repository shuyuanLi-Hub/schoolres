package org.shuyuan.schoolres.service.order.impl;

import org.shuyuan.schoolres.dao.OrderDao;
import org.shuyuan.schoolres.dao.OrderDishesDao;
import org.shuyuan.schoolres.dao.RiderDao;
import org.shuyuan.schoolres.dao.UserDao;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class OrderServiceImpl implements OrderService
{
    private OrderDao dao;
    private OrderDishesDao dishesDao;
    private RiderDao riderDao;
    private UserDao userDao;

    public OrderServiceImpl(OrderDao dao, OrderDishesDao dishesDao, RiderDao riderDao, UserDao userDao)
    {
        this.dao = dao;
        this.dishesDao = dishesDao;
        this.riderDao = riderDao;
        this.userDao = userDao;
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
