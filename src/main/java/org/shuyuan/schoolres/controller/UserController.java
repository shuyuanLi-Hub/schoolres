package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class UserController
{
    private UserService service;
    private OrderService orderService;

    public UserController(UserService service, OrderService orderService)
    {
        this.service = service;
        this.orderService = orderService;
    }

    @GetMapping("/getAddress")
    @ResponseBody
    public ResponseEntity<List<String>> getUserAddress(String name)
    {
        return new ResponseEntity<>(service.findUserAddress(name), HttpStatus.OK);
    }

    @GetMapping("/initial")
    @ResponseBody
    public ResponseEntity<User> initial(HttpSession session)
    {
        if (session.getAttribute("user_name") != null)
        {
            return new ResponseEntity<>(service.findUserByName((String) session.getAttribute("user_name")), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getOrders")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrders(HttpSession session)
    {
//        log.info((String) session.getAttribute("user_name"));
        var data = orderService.findOrderByUserAndStatusNotIn((String) session.getAttribute("user_name"),
                List.of('2', '3'));

        if (data.isEmpty())
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                data,
                HttpStatus.OK);
    }
}
