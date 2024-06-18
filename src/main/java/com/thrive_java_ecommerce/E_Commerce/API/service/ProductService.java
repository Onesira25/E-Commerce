package com.thrive_java_ecommerce.E_Commerce.API.service;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Product;
import com.thrive_java_ecommerce.E_Commerce.API.entity.User;
import com.thrive_java_ecommerce.E_Commerce.API.model.ProductRequest;
import com.thrive_java_ecommerce.E_Commerce.API.model.ProductResponse;
import com.thrive_java_ecommerce.E_Commerce.API.model.ProductSearchRequest;
import com.thrive_java_ecommerce.E_Commerce.API.model.ProductUpdateRequest;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    private ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .qty(product.getQty())
                .description(product.getDescription())
                .user_id(product.getUser().getId())
                .build();
    }

    @Transactional
    public ProductResponse add(ProductRequest request) {

        validationService.validate(request);

        // check user nya ada atau tidak
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQty(request.getQty());
        product.setDescription(request.getDescription());
        product.setUser(user);

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .qty(product.getQty())
                .description(product.getDescription())
                .user_id(request.getUser_id())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(ProductSearchRequest request){
        Specification<Product> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getName())){
                predicates.add(
                        builder.like(root.get("name"), "%"+request.getName()+"%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());
        Page<Product> products= productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream()
                .map(this::toProductResponse)
                .toList();

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());

    }

    @Transactional(readOnly = true)
    public ProductResponse getById(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(ProductUpdateRequest request){
        // pastikan product nya ada
        Product product = productRepository.findById(request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQty(request.getQty());
        product.setDescription(request.getDescription());
        productRepository.save(product);

        return toProductResponse(product);
    }

    @Transactional
    public void delete(String productId){
        // pastikan product nya ada
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        productRepository.delete(product);
    }
}