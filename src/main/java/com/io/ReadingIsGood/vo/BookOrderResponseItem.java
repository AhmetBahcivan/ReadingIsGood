package com.io.ReadingIsGood.vo;

import lombok.*;

import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderResponseItem {

    private UUID bookId;
    private String name;
    private int count;
    private double price;
}
