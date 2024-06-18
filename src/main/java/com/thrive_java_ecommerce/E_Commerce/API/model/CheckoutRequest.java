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
public class CheckoutRequest {
//    @Size(max = 255)
//    @JsonProperty("user_id")
//    private String user_id;

    @Size(max = 255)
    @JsonProperty("cart_id")
    private String cart_id;

    @NotBlank
    private String address;

    @NotBlank
    private String payment_type;

}
