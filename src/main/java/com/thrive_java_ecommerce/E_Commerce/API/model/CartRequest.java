package com.thrive_java_ecommerce.E_Commerce.API.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {
    @Size(max = 255)
    @JsonProperty("user_id")
    private String user_id;

    @Size(max = 255)
    @JsonProperty("product_id")
    private String product_id;

    @NotNull
    private Integer qty;
}
