package org.shuyuan.schoolres.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AddressDaoTest
{
    @Autowired
    private AddressDao dao;

    @ParameterizedTest
    @CsvSource({"湖南省长沙市娄底市, 3"})
    public void findAddressByDetailAndUserId(String detail, Integer id)
    {
        System.out.println(dao.findAddressByDetailAndUserId(detail, id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void findAddressById(Integer id)
    {
        System.out.println(dao.findById(id));
    }
}
