package org.shuyuan.schoolres.controller;


import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Shop;
import org.shuyuan.schoolres.domain.TimeDishes;
import org.shuyuan.schoolres.service.dishes.DishesService;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.orderDishes.OrderDishesService;
import org.shuyuan.schoolres.service.shop.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Slf4j
@Controller

//@RequestMapping("/manager")
public class ManagerController
{
    private OrderDishesService orderDishesService;
    private OrderService orderService;
    private DishesService dishesService;
    private ShopService shopService;

    private static final String FILE_PATH = "D:\\everyday\\schoolres\\images\\";

    public ManagerController(OrderDishesService orderDishesService, OrderService orderService, DishesService dishesService, ShopService shopService)
    {
        this.orderDishesService = orderDishesService;
        this.orderService = orderService;
        this.dishesService = dishesService;
        this.shopService = shopService;
    }

    @GetMapping("/manager/{shop}")
    public String page(@PathVariable String shop, HttpSession session, Model model)
    {
//        log.info("开始处理 :" + shop);
        if (Objects.nonNull(shop) && shop.equals(session.getAttribute("shop")))
        {
            model.addAttribute("shop", shop + "-" + session.getAttribute("category"));
            return "manager";
        }

        return "managerLogin";
    }

    @GetMapping("/manager")
    public String getManager()
    {
        return "manager";
    }

    @GetMapping("/managerLogin")
    public String login()
    {
        return "managerLogin";
    }

    @GetMapping("/managerOrders")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrders(String name, Integer category)
    {
        List<Order.OrderDishes> orderDishes = orderDishesService.findOrderDishesByShop(name, category);
        List<Order> orders = new ArrayList<>();
        orderDishes.forEach((e) -> {
            orders.add(orderService.findOrderById(e.getOrder().getId()));
        });
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/managerLogin")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(Shop shop, HttpSession session)
    {
        System.out.println(shop.getAccount() + " : " + shop.getPasswd());
        var obj = shopService.findShopByAccountAndPasswd(shop.getAccount(), shop.getPasswd());
        if (Objects.nonNull(obj))
        {
            session.setAttribute("shop", obj.getName());
            session.setAttribute("category", obj.getCategory());
            return new ResponseEntity<>(Map.of("status", "ok", "shop", obj.getName(), "category", obj.getCategory() + ""), HttpStatus.OK);
        }

        return new ResponseEntity<>(Map.of("status", "err"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/managerDishes")
    @ResponseBody
    public ResponseEntity<List<Dishes>> getDishes(String name)
    {
        return new ResponseEntity<>(dishesService.findDishesByShop(shopService.findShopByName(name)), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/addDishes")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addDishes(@Validated TimeDishes dishes, Errors errors, Model model)
    {
//        System.out.println(dishes);
        if (Objects.isNull(dishes.getCover()))
        {
//            errors.rejectValue("cover", null, "封面不能为空!");
            return new ResponseEntity<>(Map.of("status", "err", "field", "cover", "mess", "封面不能为空!"), HttpStatus.BAD_REQUEST);
        }
        if (dishes.getCover().getSize() > 5 * 1024 * 1024)
        {
//            errors.rejectValue("cover", null, "文件过大!");
            return new ResponseEntity<>(Map.of("status", "err", "field", "cover", "mess", "文件过大!"), HttpStatus.BAD_REQUEST);
        }
        if (!dishes.getCover().getContentType().startsWith("image"))
        {
//            errors.rejectValue("cover", null, "只能上传图片!");
            return new ResponseEntity<>(Map.of("status", "err", "field", "cover", "mess", "只能上传图片!"), HttpStatus.BAD_REQUEST);
        }

        String UUIDCover = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(dishes.getCover().getOriginalFilename());
        Dishes d = new Dishes();
        d.setName(dishes.getName());
        d.setDescription(dishes.getDesc());
        d.setPrice(dishes.getPrice());
        d.setShop(shopService.findShopByNameAndCategory(dishes.getShop(), dishes.getCategory()));
        d.setPhoto(UUIDCover);
        d.setCategory(dishes.getCategory());
        dishesService.save(d);

        File coverFile = new File(FILE_PATH + UUIDCover);
        dishes.getCover().transferTo(coverFile);

        model.addAttribute("success", "添加成功");
        return new ResponseEntity<>(Map.of("status", "ok", "mess", "上传成功"), HttpStatus.OK);
    }

    @SneakyThrows
    private <S> String[] getNullPropertyNames(S obj)
    {
        var fields = obj.getClass().getDeclaredFields();
        String[] nullFields = new String[fields.length];

        for (var i = 0; i < fields.length; i++)
        {
            fields[i].setAccessible(true);
            if (Objects.nonNull(fields[i].get(obj)))
            {
                nullFields[i] = fields[i].getName();
            }
        }

        return nullFields;
    }

    @SneakyThrows
    @PatchMapping("/updateDishes/{originName}")
    @ResponseBody
    public ResponseEntity<String> updateDishes(Dishes dishes, @PathVariable String originName)
    {
//        log.info(originName);
//        System.out.println(dishes.getCover());
        var obj = dishesService.findDishesByName(originName);
//        System.out.println(obj);
        BeanUtils.copyProperties(obj, dishes, getNullPropertyNames(dishes));

        MultipartFile cover = dishes.getCover();

        if (Objects.nonNull(cover) && !cover.isEmpty())
        {
            if (cover.getSize() > 5 * 1024 * 1024)
            {
                return new ResponseEntity<>("size", HttpStatus.METHOD_NOT_ALLOWED);
            }
            if (!cover.getContentType().startsWith("image"))
            {
                return new ResponseEntity<>("type", HttpStatus.BAD_REQUEST);
            }

            String UUIDCover = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(cover.getOriginalFilename());
            File file = new File(FILE_PATH + UUIDCover);

            cover.transferTo(file);
            dishes.setPhoto(UUIDCover);
        }

        dishesService.save(dishes);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/deleteDishes")
    @ResponseBody
    public void delete(String name)
    {
        dishesService.deleteDishes(name);
    }
}
