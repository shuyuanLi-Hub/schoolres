package org.shuyuan.schoolres.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address_inf")
@Getter
@Setter
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @Column(name = "address_detail")
    private String detail;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Address() {}

    public Address(Integer id, String detail, User user)
    {
        this.id = id;
        this.detail = detail;
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                '}';
    }
}
