package org.shuyuan.schoolres.controller;

import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.service.dishes.DishesService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class DishesController
{
    private DishesService service;
    private Integer count = 1;

    public DishesController(DishesService service)
    {
        this.service = service;
    }

    @GetMapping("/get_dishes")
    @ResponseBody
    public ResponseEntity<List<Dishes>> getDishes(String type)
    {
        if (type.equals("normal"))
        {
            return new ResponseEntity<>(service.findAllByPage(PageRequest.of(0, 6)), HttpStatus.OK);
        }
        else
        {
            List<Dishes> dishes = service.findAllByPage(PageRequest.of(count++, 6));

            if (dishes.size() < 6)
            {
                count = 1;
                return new ResponseEntity<>(service.findAllByPage(PageRequest.of(0, 6)), HttpStatus.OK);
            }

            return new ResponseEntity<>(dishes, HttpStatus.OK);
        }
    }

    @GetMapping("/init_dishes")
    @ResponseBody
    public ResponseEntity<List<Dishes>> getOneCategoryDishes(Integer category)
    {
        return new ResponseEntity<>(service.findDishesByCategory(category, 15), HttpStatus.OK);
    }

    @GetMapping("/search_dishes")
    @ResponseBody
    public ResponseEntity<List<Dishes>> getSearchDishes(String pattern)
    {
        return new ResponseEntity<>(service.findDishesByNameLikeOrShopLike(pattern), HttpStatus.OK);
    }
}
