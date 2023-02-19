package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "host_inf")
@Getter
@Setter
public class Host
{
    @Id
    @Column(name = "host_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "host_name")
    private String name;

    @Column(name = "host_passwd")
    private String passwd;

    @Column(name = "host_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "host_gender")
    private Character gender;

    @Column(name = "host_phone")
    private String phone;

    @Column(name = "host_photo")
    private String photo;

    @OneToMany(targetEntity = Shop.class, mappedBy = "host")
    private List<Shop> shops;

    public Host() {}

    public Host(Integer id, String name, String passwd, Date date, Character gender, String phone, String photo)
    {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.date = date;
        this.gender = gender;
        this.phone = phone;
        this.photo = photo;
    }

    public Host(String name, String passwd, Date date, Character gender, String phone, String photo, List<Shop> shops)
    {
        this.name = name;
        this.passwd = passwd;
        this.date = date;
        this.gender = gender;
        this.phone = phone;
        this.photo = photo;
        this.shops = shops;
    }

    @Override
    public String toString()
    {
        return "Host{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", date=" + date +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", shops=" + shops +
                '}';
    }
}
