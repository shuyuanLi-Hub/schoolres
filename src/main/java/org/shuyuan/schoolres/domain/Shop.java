package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shop_inf")
@Getter
@Setter
public class Shop
{
    @Id
    @Column(name = "shop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_name")
    private String name;

    @Column(name = "shop_category")
    private Integer category;

    @ManyToOne(targetEntity = Host.class)
    @JoinColumn(name = "shop_owner", referencedColumnName = "host_id")
    private Host host;

    @OneToMany(targetEntity = Dishes.class, mappedBy = "shop", fetch = FetchType.EAGER)
    private List<Dishes> dishes;

    @OneToMany(targetEntity = Order.OrderDishes.class, mappedBy = "shop")
    private List<Order.OrderDishes> orderDishes;

    public Shop() {}

    public Shop(Integer id)
    {
        this.id = id;
    }

    public Shop(String name, Integer category)
    {
        this.name = name;
        this.category = category;
    }

    public Shop(String name, Integer category, Host host, List<Dishes> dishes, List<Order.OrderDishes> orderDishes)
    {
        this.name = name;
        this.category = category;
        this.host = host;
        this.dishes = dishes;
        this.orderDishes = orderDishes;
    }

    public Shop(Integer id, String name, Integer category, Host host)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.host = host;
    }

    @Override
    public String toString()
    {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", dishes=" + dishes +
                ", host=" + host.getName() +
                '}';
    }
}
