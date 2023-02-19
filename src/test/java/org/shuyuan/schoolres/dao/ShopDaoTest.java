package org.shuyuan.schoolres.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.shuyuan.schoolres.domain.Host;
import org.shuyuan.schoolres.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShopDaoTest
{
    @Autowired
    private ShopDao dao;

    @ParameterizedTest
    @MethodSource("getShop")
    public void saveShop(Shop shop)
    {
        dao.save(shop);
    }

    @Test
    public void findShop()
    {
        dao.findAll().forEach(System.out::println);
    }

    public static Stream<Shop> getShop()
    {
        Host host1 = new Host();
        Host host2 = new Host();
        Host host3 = new Host();

        host1.setId(3);
        host2.setId(4);
        host3.setId(5);

        return Stream.of(
                new Shop(null,
                "烧鹅饭",
                1,
                host1),
                new Shop(null,
                "烤肉饭",
                2,
                host2),
                new Shop(null,
                "自主餐",
                2,
                host3));
    }
}
