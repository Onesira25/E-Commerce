package com.thrive_java_ecommerce.E_Commerce.API.entity;

import com.thrive_java_ecommerce.E_Commerce.API.model.CartResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.transform.sax.SAXResult;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checkouts")
public class Checkout {
    @Id
    private String id;

    private String address;

    private String payment_type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
}
