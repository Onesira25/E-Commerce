package com.thrive_java_ecommerce.E_Commerce.API.service;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Cart;
import com.thrive_java_ecommerce.E_Commerce.API.entity.Product;
import com.thrive_java_ecommerce.E_Commerce.API.entity.User;
import com.thrive_java_ecommerce.E_Commerce.API.model.*;
import com.thrive_java_ecommerce.E_Commerce.API.repository.CartRepository;
import com.thrive_java_ecommerce.E_Commerce.API.repository.ProductRepository;
import com.thrive_java_ecommerce.E_Commerce.API.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    private CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .qty(cart.getQty())
                .total_price(cart.getTotal_price())
                .user_id(cart.getUser().getId())
                .product_id(cart.getProduct_id().getId())
//                .products(cart.getProducts())
                .build();
    }

    @Transactional
    public CartResponse add(CartRequest request) {

        validationService.validate(request);

        // check user nya ada atau tidak
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        // check proudct nya ada atau tidak
        Product product = productRepository.findById(request.getProduct_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setQty(request.getQty());
        cart.setUser(user);
        cart.setProduct_id(product);

//        if (cart.getTotal_price() == null) {
//            Integer currentTotalPrice = (cart.getTotal_price() + (product.getPrice()* request.getQty()));
//
//        }
//        Integer currentTotalPrice = (product.getPrice() * request.getQty());

        cart.setTotal_price(product.getPrice() * request.getQty());

//        cart.setProducts(product);

        cartRepository.save(cart);

        return CartResponse.builder()
                .id(cart.getId())
                .qty(cart.getQty())
                .total_price(cart.getTotal_price())
                .user_id(request.getUser_id())
                .product_id(request.getProduct_id())
//                .products(product)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<CartResponse> getAllCart(GetAllCartRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        Page<Cart> carts = cartRepository.findAll(pageable);
        List<CartResponse> cartResponses = carts.getContent().stream()
                .map(this::toCartResponse)
                .toList();

        return new PageImpl<>(cartResponses, pageable, carts.getTotalElements());
    }

    @Transactional
    public CartResponse edit(CartEditRequest request){
        // pastikan cart nya ada
        Cart cart = cartRepository.findById(request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found"));
        // cek product
        Product product = productRepository.findById(cart.getProduct_id().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        cart.setQty(request.getQty());
        cart.setTotal_price(product.getPrice() * request.getQty());
        cartRepository.save(cart);

        return toCartResponse(cart);
    }

    @Transactional
    public void delete(String cartId){
        // pastikan cart nya ada
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart not found"));

        cartRepository.delete(cart);
    }
}
