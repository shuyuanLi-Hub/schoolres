package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDao extends CrudRepository<Order, Integer>
{
    List<Order> findOrderByStatus(Character status);

    List<Order> findOrderByRiderAndStatus(Rider rider, Character status);

    List<Order> findOrderByRider(Rider rider);

    @Query("update Order order set order.status = ?1, order.rider = ?2 where order.id = ?3")
    @Modifying
    void updateStatusAndRider(Character status, Rider rider, Integer id);

    @Query("update Order order set order.status = ?1 where order.id = ?2")
    @Modifying
    void updateStatusById(Character status, Integer id);

    List<Order> findOrderByUserAndStatus(User user, Character status);

    List<Order> findOrderByUserAndStatusNotIn(User user, List<Character> statuses);

}
