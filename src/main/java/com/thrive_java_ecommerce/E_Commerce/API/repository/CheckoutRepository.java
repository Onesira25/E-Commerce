package com.thrive_java_ecommerce.E_Commerce.API.repository;

import com.thrive_java_ecommerce.E_Commerce.API.entity.Checkout;
import com.thrive_java_ecommerce.E_Commerce.API.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, String>, JpaSpecificationExecutor<Checkout> {
}
