package org.shuyuan.schoolres.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.shuyuan.schoolres.domain.Dishes;
import org.shuyuan.schoolres.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DishesDaoTest
{
    @Autowired
    private DishesDao dao;
    @Autowired
    private ShopDao shopDao;

    @ParameterizedTest
    @CsvSource({
//              "土匪猪肝, 16.00, '土匪吃的猪肝', 1, 'test_1.jpg', 1",
//              "铁板牛肉, 19.00, '非常nice的牛肉', 2, 'test_2.jpg', 2"
//            "北京烤鸭, 23.00, '非常的好吃', 2, 'test_one.jpg', 4",
//            "辣子鸡, 13.00, '极致的美味', 1, 'test_two.jpg', 1",
//            "西湖醋鱼, 18.00, '特别的鲜美', 2, 'test_three.jpg', 2",
//            "飞龙汤, 15.00, '飞龙在天', 1, 'test_four.jpg', 3",
//            "无为熏鸭, 15.00, '超级熏鸭', 2, 'test_five.jpg', 4",
//            "东坡肉, 15.00, '东坡做的东坡肉', 1, 'test_six.jpg', 1",
//            "腊味合蒸, 19.00, '超级蒸菜', 2, 'test_seven.jpg', 2",
//            "农家焖猪杂, 13.00, '猪肉大杂烩', 1, 'test_eight.jpg', 3",
//            "酱板鸭, 20.00, '超级无敌酱板鸭', 2, 'test_nine.jpg', 4"
            "东坡肘子, 16.00, '东坡做的肘子', 1, 'dishes_one_one.jpg', 1",
            "佛跳墙, 30.00, '非常nice，懂得都懂', 2, 'dishes_one_two.jpg', 2",
            "客家盐焗鸡, 16.00, '好吃就完了', 1, 'dishes_one_three.jpg', 3",
            "金陵丸子, 14.00, '非常nice，懂得都懂', 2, 'dishes_one_four.jpg', 4"
    })
    public void saveDishes(String name, Double price, String desc, Integer category, String photo, Integer id)
    {
        dao.save(new Dishes(null, name, desc, price, category, photo, new Shop(id)));
    }

    @Test
    public void findAll()
    {
        dao.findAll().forEach(System.out::println);
    }

    @Test
    public void findAllByPage()
    {
        dao.findAll(PageRequest.of(0, 6)).forEach(System.out::println);
    }

    @ParameterizedTest
    @CsvSource({"1, 0, 15"})
    public void findDishesByCategory(Integer category, Integer page, Integer size)
    {
        dao.findDishesByCategory(category, PageRequest.of(page, size)).forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {"烤鸭"})
    public void findDishesByNameLikeOrShopLike(String pattern)
    {
        dao.findDishesByNameLikeOrShopLike(pattern).forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {"烧鹅饭"})
    public void findDishesByShop(String name)
    {
        dao.findDishesByShop(shopDao.findShopByName(name)).forEach(System.out::println);
    }
}
