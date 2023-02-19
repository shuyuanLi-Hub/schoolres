package org.shuyuan.schoolres.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.shuyuan.schoolres.domain.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderDaoTest
{
    @Autowired
    private OrderDao dao;

    @Test
    public void findOrder()
    {
        dao.findAll().forEach(e -> {
            e.getDishes().forEach(System.out::println);
        });
    }

    @ParameterizedTest
    @CsvSource({"1, 25"})
    @Transactional
    @Rollback(value = false)
    public void update(Integer rider, Integer id)
    {
        dao.updateStatusAndRider('1', new Rider(rider), id);
    }
}
