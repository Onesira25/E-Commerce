package com.thrive_java_ecommerce.E_Commerce.API.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {
    private String name;

    @NotNull
    private Integer page;

    @NotNull
    private Integer limit;
}


