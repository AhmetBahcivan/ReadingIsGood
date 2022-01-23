package com.io.ReadingIsGood.vo;

import lombok.*;

import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderItem {

    private UUID bookId;
    private int count;
    //private double price;

}
