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
public class SearchUserRequest {

    private String name;

    private String email;

    @NotNull
    private Integer page;

    @NotNull
    private Integer limit;
}
