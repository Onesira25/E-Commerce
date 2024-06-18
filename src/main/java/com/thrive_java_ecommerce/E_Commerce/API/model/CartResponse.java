package com.thrive_java_ecommerce.E_Commerce.API.model;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXResult;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String id;

    private Integer qty;

    private Integer total_price;

    private String user_id;

    private String product_id;

//    private Product products;
}





