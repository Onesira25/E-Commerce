package com.thrive_java_ecommerce.E_Commerce.API.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Product> products;
}
