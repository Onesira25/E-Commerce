package com.thrive_java_ecommerce.E_Commerce.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
    private String id;

    private String address;

    private String payment_type;

    private String user_id;

    private String cart_id;
}
