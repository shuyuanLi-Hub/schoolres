package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.shop.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
public class OrderController
{
    private OrderService service;
    private ShopService shopService;

    public OrderController(OrderService service, ShopService shopService)
    {
        this.service = service;
        this.shopService = shopService;
    }

    @SneakyThrows
    @PostMapping("/orderOver")
    @ResponseBody
    public void orderOver(@RequestBody List<Map<String, String>> data, HttpSession session)
    {
        Calendar calendar = Calendar.getInstance();

        Map<String, Map<Order, List<Order.OrderDishes>>> orderMap = new HashMap<>();

        data.forEach(e -> {
            if (orderMap.get(e.get("shop")) == null)
            {
                Map<Order, List<Order.OrderDishes>> od = new HashMap<>();
                Order order = new Order(calendar.getTime(),
                        new User((Integer) session.getAttribute("user_id")),
                        '0',
                        e.get("address"),
                        e.get("remark"));

                List<Order.OrderDishes> orderDishes = new ArrayList<>();
                orderDishes.add(new Order.OrderDishes(
                        new Dishes(Integer.valueOf(e.get("id"))),
                        null ,
                        Integer.valueOf(e.get("count")),
                        new Shop(shopService.findShopByName(e.get("shop")).getId())));

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
                            new Shop(shopService.findShopByName(e.get("shop")).getId())));
                });
            }
        });

        orderMap.forEach((k, v) -> {
            v.forEach((i, j) -> {
                service.save(i, j);
            });
        });
    }

    @GetMapping("/getHistory")
    @ResponseBody
    public ResponseEntity<List<Order>> getHistory(HttpSession session)
    {
        if (session.getAttribute("user_name") != null)
        {
            var data = service.findOrderByUserAndStatusNotIn((String) session.getAttribute("user_name"), List.of('0', '1'));

            return new ResponseEntity<>(data, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
