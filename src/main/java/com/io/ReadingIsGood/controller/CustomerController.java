package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.service.CustomerService;
import com.io.ReadingIsGood.vo.LoginForm;
import com.io.ReadingIsGood.vo.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signUp")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signUpRequest, HttpServletRequest request) {
        return customerService.registerUser(signUpRequest, request);
    }

    /*
    @PostMapping("/signIn")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        return customerService.authenticateUser(loginRequest);
    }
     */
}
