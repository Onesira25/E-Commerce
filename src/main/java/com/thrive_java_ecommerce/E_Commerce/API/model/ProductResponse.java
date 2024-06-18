package com.thrive_java_ecommerce.E_Commerce.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;

    private String user_id;

    private String name;

    private Integer price;

    private Integer qty;

    private String description;
}
