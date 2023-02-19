package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

@Entity
@Table(name = "hot_dishes_inf")
@Data
public class HotDishes
{
    @Id
    @Column(name = "hot_dishes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer hot;

    @OneToOne(targetEntity = Dishes.class)
    @JoinColumn(name = "dishes_id", referencedColumnName = "dishes_id")
    private Dishes dishes;
}
