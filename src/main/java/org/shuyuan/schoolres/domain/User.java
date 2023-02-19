package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_inf")
public class User
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "user_name")
    private String name;

    @Past()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "user_date")
    private Date date;

    @Column(name = "user_passwd")
    private String passwd;

    @Column(name = "user_phone")
    @Length(min = 11, max = 11)
    private String phone;

    @Email
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_gender")
    private Character gender;

    @Column(name = "user_photo")
    private String photo;

    @OneToMany(targetEntity = Address.class, mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(targetEntity = Order.class, mappedBy = "user")
    private List<Order> orders;

    @OneToMany(targetEntity = Message.class, mappedBy = "user")
    private List<Message> messages;

    public User() {}

    public User(Integer id)
    {
        this.id = id;
    }

    public User(Integer id, String name, Date date, String passwd, String phone, String email, Character gender)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.passwd = passwd;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", passwd='" + passwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", photo='" + photo + '\'' +
                '}';
    }
}