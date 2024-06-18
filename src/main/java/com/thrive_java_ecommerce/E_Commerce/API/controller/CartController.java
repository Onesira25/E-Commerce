package com.thrive_java_ecommerce.E_Commerce.API.controller;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Cart;
import com.thrive_java_ecommerce.E_Commerce.API.model.*;
import com.thrive_java_ecommerce.E_Commerce.API.service.CartService;
import com.thrive_java_ecommerce.E_Commerce.API.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping(
            path = "/carts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CartResponse> add(@RequestBody CartRequest request) {
        CartResponse cartResponse = cartService.add(request);
        return WebResponse.<CartResponse>builder()
                .message("success add to cart")
                .data(cartResponse)
                .build();
    }

    @GetMapping(
            path = "/carts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CartResponse>> getAllCart(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
         GetAllCartRequest request = GetAllCartRequest.builder()
                .page(page)
                .limit(limit)
                .build();

        log.info("page: "+page.toString());
        Page<CartResponse> cartResponses = cartService.getAllCart(request);
        return WebResponse.<List<CartResponse>>builder()
                .data(cartResponses.getContent())
                .message("success")
                .pagination(PaginationResponse.builder()
                        .currentPage(cartResponses.getNumber())
                        .totalPage(cartResponses.getTotalPages())
                        .limit(cartResponses.getSize())
                        .build())
                .build();
    }

    @PutMapping(
            path = "/carts/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CartResponse> edit(
            @RequestBody CartEditRequest request,
            @PathVariable("id") String cartId
    ){
        request.setId(cartId);
        CartResponse cartResponse = cartService.edit(request);
        return WebResponse.<CartResponse>builder()
                .message("success edit cart")
                .data(cartResponse)
                .build();
    }

    @DeleteMapping(
            path = "/carts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("id") String cartId){
        cartService.delete(cartId);
        return WebResponse.<String>builder()
                .message("success delete cart")
                .build();
    }
}
