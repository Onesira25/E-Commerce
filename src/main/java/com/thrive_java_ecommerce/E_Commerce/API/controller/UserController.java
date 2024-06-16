package com.thrive_java_ecommerce.E_Commerce.API.controller;

import com.thrive_java_ecommerce.E_Commerce.API.model.*;
import com.thrive_java_ecommerce.E_Commerce.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    //inject
    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> add(@RequestBody UserRequest request){
        userService.Create(request);
        return WebResponse.<String>builder()
                .message("success add data")
                .build();
    }


    @PutMapping(
            path = "/users/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(
            @RequestBody UpdateUserRequest request,
            @PathVariable("userId") String userId
    ){
        // ambil dari url param dan set ke var request
        request.setId(userId);

        UserResponse userResponse = userService.update(request);
        return WebResponse.<UserResponse>builder()
                .message("success update data")
                .data(userResponse)
                .build();
    }

    @DeleteMapping(
            path = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(
            @PathVariable("userId") String userId
    ){
        userService.delete(userId);
        return WebResponse.<String>builder()
                .message("success delete data")
                .build();
    }

    @GetMapping(
            path = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> getById(
            @PathVariable("userId") String userId
    ){
        UserResponse userResponse = userService.getById(userId);
        return WebResponse.<UserResponse>builder()
                .message("success get data")
                .data(userResponse)
                .build();
    }

    @GetMapping(
            path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>> getAllOrSearch(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
        SearchUserRequest request = SearchUserRequest.builder()
                .name(name)
                .email(email)
                .page(page)
                .limit(limit)
                .build();

        Page<UserResponse> userResponses = userService.getAllOrSearch(request);
        return WebResponse.<List<UserResponse>>builder()
                .message("success get data")
                .data(userResponses.getContent())
                .pagination(PaginationResponse.builder()
                        .currentPage(userResponses.getNumber())
                        .totalPage(userResponses.getTotalPages())
                        .limit(userResponses.getSize())
                        .build())
                .build();
    }


}
