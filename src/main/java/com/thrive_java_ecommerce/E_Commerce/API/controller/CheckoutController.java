package com.thrive_java_ecommerce.E_Commerce.API.controller;

import com.thrive_java_ecommerce.E_Commerce.API.model.*;
import com.thrive_java_ecommerce.E_Commerce.API.service.CartService;
import com.thrive_java_ecommerce.E_Commerce.API.service.CheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CheckoutController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping(
            path = "/checkouts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CheckoutResponse> create(@RequestBody CheckoutRequest request) {
        CheckoutResponse checkoutResponse = checkoutService.create(request);
        return WebResponse.<CheckoutResponse>builder()
                .message("success")
                .data(checkoutResponse)
                .build();
    }

    @GetMapping(
            path = "/checkouts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CheckoutResponse>> getCheckoutHistory(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
        GetCheckoutHistory request = GetCheckoutHistory.builder()
                .page(page)
                .limit(limit)
                .build();

        log.info("page: "+page.toString());
        Page<CheckoutResponse> checkoutResponses = checkoutService.getAllCheckout(request);
        return WebResponse.<List<CheckoutResponse>>builder()
                .data(checkoutResponses.getContent())
                .message("success")
                .pagination(PaginationResponse.builder()
                        .currentPage(checkoutResponses.getNumber())
                        .totalPage(checkoutResponses.getTotalPages())
                        .limit(checkoutResponses.getSize())
                        .build())
                .build();
    }
}
