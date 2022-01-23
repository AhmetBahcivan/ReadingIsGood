package com.io.ReadingIsGood.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@ToString
@Entity
@Setter
@Getter
@Table(name="bookOrder", schema = "public")
@NoArgsConstructor
public class BookOrder {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    private int count;
    private double bookPrice;

    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BookOrder (int count, double bookPrice, Book book, Order order) {
        this.count = count;
        this.bookPrice = bookPrice;
        this.book = book;
        this.order = order;
    }

}
