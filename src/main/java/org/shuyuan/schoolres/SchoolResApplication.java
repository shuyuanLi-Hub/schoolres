package org.shuyuan.schoolres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SchoolResApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SchoolResApplication.class, args);
    }

}
