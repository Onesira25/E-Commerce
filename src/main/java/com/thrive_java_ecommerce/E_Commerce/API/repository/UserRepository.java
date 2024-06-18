package com.thrive_java_ecommerce.E_Commerce.API.repository;

import com.thrive_java_ecommerce.E_Commerce.API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User findByEmail(String email, String password);
}
