package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.exceptions.CodeMsg;
import org.shuyuan.schoolres.exceptions.UserException;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.order.impl.OrderServiceImpl;
import org.shuyuan.schoolres.service.shop.ShopService;
import org.shuyuan.schoolres.service.user.UserService;
import org.shuyuan.schoolres.utils.CookieUtil;
import org.shuyuan.schoolres.utils.RabbitMQUtil;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.support.RabbitExceptionTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
public class OrderController
{
    private OrderService service;
    private ShopService shopService;
    private UserService userService;
    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;

    public OrderController(OrderService service, ShopService shopService, UserService userService, AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin)
    {
        this.service = service;
        this.shopService = shopService;
        this.userService = userService;
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @SneakyThrows
    @PostMapping("/orderOver/{address}")
    @ResponseBody
    @Async
    public void overOrder(@RequestBody List<Map<String, String>> orders, @PathVariable("address") String addr, @MatrixVariable(required = false) String remark, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        orders.add(Map.of("user", "" + user.getId()));

        if (remark == null)
        {
            orders.add(Map.of("addr", addr));
        }
        else
        {
            orders.add(Map.of("addr", addr, "remark", remark));
        }

        service.orderOver(amqpAdmin, amqpTemplate, orders);
    }
}
