package org.shuyuan.schoolres.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TimeDishes
{
    @NotBlank(message = "名字不能为空!")
    @Length(max = 20, min = 1, message = "长度非法!")
    private String name;
    private String desc;
    @Range(min = 0, max = 1000, message = "价格非法")
    private Double price;
    private MultipartFile cover;

    @NotBlank(message = "商家不能为空")
    private String shop;

    @Range(min = 1, max = 2, message = "类别不存在")
    private Integer category;

    public TimeDishes() {}

    public TimeDishes(String name, String desc, Double price, MultipartFile cover, String shop, Integer category)
    {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.cover = cover;
        this.shop = shop;
        this.category = category;
    }
}
