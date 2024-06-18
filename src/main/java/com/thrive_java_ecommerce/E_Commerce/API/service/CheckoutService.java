package com.thrive_java_ecommerce.E_Commerce.API.service;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Cart;
import com.thrive_java_ecommerce.E_Commerce.API.entity.Checkout;
import com.thrive_java_ecommerce.E_Commerce.API.entity.Product;
import com.thrive_java_ecommerce.E_Commerce.API.entity.User;
import com.thrive_java_ecommerce.E_Commerce.API.model.*;
import com.thrive_java_ecommerce.E_Commerce.API.repository.CartRepository;
import com.thrive_java_ecommerce.E_Commerce.API.repository.CheckoutRepository;
import com.thrive_java_ecommerce.E_Commerce.API.repository.ProductRepository;
import com.thrive_java_ecommerce.E_Commerce.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {
    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    private CheckoutResponse toCheckoutResponse(Checkout checkout) {
        return CheckoutResponse.builder()
                .id(checkout.getId())
                .address(checkout.getAddress())
                .payment_type(checkout.getPayment_type())
                .user_id(checkout.getUser().getId())
                .cart_id(checkout.getCart().getId())
                .build();
    }

    @Transactional
    public CheckoutResponse create(CheckoutRequest request) {

        validationService.validate(request);

        // check ada atau tidak
        Cart cart = cartRepository.findById(request.getCart_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found"));
        User user = userRepository.findById(cart.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Product product = productRepository.findById(cart.getProduct_id().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        Checkout checkout = new Checkout();
        checkout.setId(UUID.randomUUID().toString());
        checkout.setAddress(request.getAddress());
        checkout.setPayment_type(request.getPayment_type());
        checkout.setUser(user);
        checkout.setCart(cart);

        checkoutRepository.save(checkout);

        product.setQty(product.getQty() - cart.getQty());

        productRepository.save(product);

        return CheckoutResponse.builder()
                .id(checkout.getId())
                .address(checkout.getAddress())
                .payment_type(checkout.getPayment_type())
                .cart_id(request.getCart_id())
                .user_id(cart.getUser().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<CheckoutResponse> getAllCheckout(GetCheckoutHistory request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        Page<Checkout> checkouts = checkoutRepository.findAll(pageable);
        List<CheckoutResponse> checkoutResponses = checkouts.getContent().stream()
                .map(this::toCheckoutResponse)
                .toList();

        return new PageImpl<>(checkoutResponses, pageable, checkouts.getTotalElements());
    }
}
