package org.shuyuan.schoolres.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.naming.Name;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_inf")
@Getter
@Setter
public class Order
{
    @Table(name = "order_dishes_inf")
    @Entity
    @Getter
    @Setter
    public static class OrderDishes {
        @Id
        @Column(name = "order_dishes_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne(targetEntity = Dishes.class)
        @JoinColumn(name = "dishes_id", referencedColumnName = "dishes_id")
        private Dishes dishes;

        @JsonIgnore
        @ManyToOne(targetEntity = Order.class)
        @JoinColumn(name = "order_id", referencedColumnName = "order_id")
        private Order order;

        @ManyToOne(targetEntity = Shop.class)
        @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
        private Shop shop;

        @Column(name = "order_dishes_count")
        private Integer count;

//        @ColumnDefault("0")
        @Column(name = "order_dishes_status")
        private Character status;

        public OrderDishes() {}

        public OrderDishes(Dishes dishes, Order order, Integer count, Shop shop)
        {
            this.dishes = dishes;
            this.order = order;
            this.count = count;
            this.shop = shop;
        }

        @Override
        public String toString()
        {
            return "OrderDishes{" +
                    "dishes=" + dishes.getName() +
                    ", order=" + order.getId() +
                    ", shop=" + shop.getName() +
                    ", count=" + count +
                    '}';
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(targetEntity = Rider.class)
    @JoinColumn(name = "rider_id", referencedColumnName = "rider_id")
    private Rider rider;

    @OneToMany(targetEntity = OrderDishes.class, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDishes> dishes;

    @Column(name = "order_status")
    private Character status;

    @Column(name = "order_addr")
    private String address;

    @Column(name = "order_remark")
    private String remark;

    public Order() {}

    public Order(Integer id, Date date, User user, Rider rider, List<OrderDishes> dishes, Character status)
    {
        this.id = id;
        this.date = date;
        this.user = user;
        this.rider = rider;
        this.dishes = dishes;
        this.status = status;
    }

    public Order(List<OrderDishes> dishes, Character status)
    {
        this.dishes = dishes;
        this.status = status;
    }

    public Order(Date date, User user, Character status, String address, String remark)
    {
        this.date = date;
        this.user = user;
        this.status = status;
        this.address = address;
        this.remark = remark;
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user.getName() +
//                ", rider=" + rider.getName() +
                ", dishes=" + dishes.toString() +
                '}';
    }
}
