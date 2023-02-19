package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rider_inf")
@Getter
@Setter
public class Rider
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id")
    private Integer id;

    @OneToMany(targetEntity = Order.class, mappedBy = "rider")
    private List<Order> orders;

    @Column(name = "rider_name")
    private String name;

    @Column(name = "rider_session_id")
    private String sessionId;

    @Column(name = "rider_open_id")
    private String openId;

    @OneToMany(targetEntity = Message.class, mappedBy = "rider")
    private List<Message> messages;

    public Rider() {}

    public Rider(Integer id)
    {
        this.id = id;
    }

    public Rider(String name)
    {
        this.name = name;
    }

    public Rider(String name, String sessionId, String openId)
    {
        this.name = name;
        this.sessionId = sessionId;
        this.openId = openId;
    }
}
