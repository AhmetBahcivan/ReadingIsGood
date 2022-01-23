package com.io.ReadingIsGood.vo;

import com.io.ReadingIsGood.db.entity.Customer;
import lombok.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseItem {

    private UUID orderId;
    private Customer customer;
    private List<BookOrderResponseItem> orderItemList;
    private double totalPrice;
    private Timestamp orderDate;
}
