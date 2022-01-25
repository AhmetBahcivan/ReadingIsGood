package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.service.CustomerService;
import com.io.ReadingIsGood.vo.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/auth/signUp")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        return customerService.registerUser(signUpRequest);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity getAllOrders(int pageNum, int limit) {
        return customerService.getAllOrdersFromUser(pageNum, limit);
    }

    /*
    @PostMapping("/signIn")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        return customerService.authenticateUser(loginRequest);
    }

     */

}
