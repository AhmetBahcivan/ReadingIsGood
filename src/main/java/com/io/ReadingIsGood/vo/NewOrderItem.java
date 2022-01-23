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
public class NewOrderItem {

    //private UUID customerId;
    private List<BookOrderItem> orderItemList;
    //private double totalPrice;
    //private Timestamp orderDate;

}
