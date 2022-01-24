package com.io.ReadingIsGood.vo;

public interface MonthlyStatistic {
    Integer getTotalOrderCount();
    Integer getTotalBookCount();
    Double getTotalPurchasedAmount();
    String getMonth();
}
