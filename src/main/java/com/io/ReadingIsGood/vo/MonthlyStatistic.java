package com.io.ReadingIsGood.vo;

public interface MonthlyStatistic {
    Integer totalOrderCount();
    Integer totalBookCount();
    Double totalPurchasedAmount();
    String month();
}
