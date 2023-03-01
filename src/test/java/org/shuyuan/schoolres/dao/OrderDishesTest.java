package org.shuyuan.schoolres.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.shuyuan.schoolres.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDishesTest
{
    @Autowired
    private OrderDishesDao dao;

    @Autowired
    private ShopDao shopDao;

    @Test
    public void findAll()
    {
        dao.findAll().forEach(System.out::println);
    }

    @Test
    public void findOrderDishsByShop()
    {
        System.out.println(dao.findOrderDishesByShop(shopDao.findShopByName("港式卤肉饭")));
    }

    @ParameterizedTest
    @CsvSource({"18"})
    public void findOrderDishesById(Integer id)
    {
        System.out.println(dao.findOrderDishesById(id));
    }
}
