package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "message_inf")
@Getter
@Setter
public class Message
{
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message_content")
    private String content;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    @Column(name = "message_time")
    private Date time;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(targetEntity = Rider.class)
    @JoinColumn(name = "rider_id", referencedColumnName = "rider_id")
    private Rider rider;

    @Column(name = "message_tag")
    private Character tag;

    public Message(String content, Date time, User user, Rider rider, Character tag)
    {
        this.content = content;
        this.time = time;
        this.user = user;
        this.rider = rider;
        this.tag = tag;
    }
}
