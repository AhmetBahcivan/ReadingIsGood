package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getMonthlyOrders")
    public ResponseEntity getMonthlyStatistics() {
        return orderService.getMonthlyStatistics();
    }
}
