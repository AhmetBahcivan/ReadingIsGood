package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.service.CustomerService;
import com.io.ReadingIsGood.service.OrderService;
import com.io.ReadingIsGood.vo.NewOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity createOrder(@Valid @RequestBody NewOrderItem newOrderItem) {
        return orderService.createOrder(newOrderItem);
    }

    @GetMapping("/getById")
    public ResponseEntity getOrderById(String orderId) {
        System.out.println("it is working");
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/getByDates")
    public ResponseEntity getOrdersByDateInterval(@DateTimeFormat(pattern="dd-MM-yyyy HH:mm") Date startDate, @DateTimeFormat(pattern="dd-MM-yyyy HH:mm") Date endDate) {

        return orderService.getOrdersByDates(startDate, endDate);
    }

}
