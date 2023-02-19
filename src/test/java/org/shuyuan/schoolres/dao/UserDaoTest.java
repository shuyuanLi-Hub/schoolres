package org.shuyuan.schoolres.dao;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class UserDaoTest
{
    @Autowired
    private UserDao dao;

    @ParameterizedTest
    @ValueSource(strings = {"shuyuan"})
    public void findUserByName(String name)
    {
        System.out.println(dao.findUserByName(name));
    }

}
