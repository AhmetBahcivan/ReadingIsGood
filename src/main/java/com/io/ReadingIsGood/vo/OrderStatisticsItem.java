package com.io.ReadingIsGood.vo;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticsItem {

    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
    private String month;

}
