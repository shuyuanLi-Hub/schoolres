package org.shuyuan.schoolres.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.shuyuan.schoolres.domain.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HostDaoTest
{
    @Autowired
    private HostDao dao;

    @ParameterizedTest
    @MethodSource("getHost")
    public void saveHost(Host host)
    {
        dao.save(host);
    }

    @SneakyThrows
    public static Stream<Host> getHost()
    {
        return Stream.of(
                new Host(
                        null,
                        "邦谷",
                        "32147",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1999-07-06"),
                        '男',
                        "19537814593",
                        "host_test.jpg"
                ),
                new Host(
                        null,
                        "恶狼",
                        "32148",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1999-07-06"),
                        '男',
                        "19537814593",
                        "host_test.jpg"
                ),
                new Host(
                        null,
                        "波罗斯",
                        "32149",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1999-07-06"),
                        '男',
                        "19537814593",
                        "host_test.jpg"
                ));
    }
}
