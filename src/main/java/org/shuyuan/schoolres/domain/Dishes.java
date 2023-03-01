package org.shuyuan.schoolres.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Table(name = "dishes_inf")
@Setter
@Getter
public class Dishes
{
    @Id
    @Column(name = "dishes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dishes_name", unique = true)
    @NotBlank
    private String name;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
    private Shop shop;

    @Column(name = "dishes_desc")
    private String description;

    @Column(name = "dishes_price")
    private Double price;

    @Column(name = "dishes_category")
    private Integer category;

    @Transient
    private MultipartFile cover;

    @Column(name = "dishes_photo")
    private String photo;

    @OneToMany(targetEntity = Order.OrderDishes.class, mappedBy = "dishes")
    private List<Order.OrderDishes> orderDishes;

    public Dishes() {}

    public Dishes(Integer id)
    {
        this.id = id;
    }

    public Dishes(String name, String desc, Double price, MultipartFile cover)
    {
        this.name = name;
        this.description = desc;
        this.price = price;
        this.cover = cover;
    }

    public Dishes(Integer id, String name, String description, Double price, Integer category, String photo, Shop shop)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.photo = photo;
        this.shop = shop;
    }

    public Dishes(String name, Shop shop, String description, Double price, Integer category, String photo, List<Order.OrderDishes> orderDishes)
    {
        this.name = name;
        this.shop = shop;
        this.description = description;
        this.price = price;
        this.category = category;
        this.photo = photo;
        this.orderDishes = orderDishes;
    }

//    public Integer getId()
//    {
//        return id;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    @JsonBackReference
//    public Shop getShop()
//    {
//        return shop;
//    }
//
//    public String getDescription()
//    {
//        return description;
//    }
//
//    public Double getPrice()
//    {
//        return price;
//    }
//
//    public Integer getCategory()
//    {
//        return category;
//    }
//
//    public String getPhoto()
//    {
//        return photo;
//    }

    @Override
    public String toString()
    {
        return "Dishes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", photo='" + photo + '\'' +
                ", shop='" + shop.getName() + '\'' +
                '}';
    }
}
