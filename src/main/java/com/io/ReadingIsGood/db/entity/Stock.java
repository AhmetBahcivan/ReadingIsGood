package com.io.ReadingIsGood.db.entity;

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
@Table(name="stock")
@NoArgsConstructor
public class Stock {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;


    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int availableCount;
    private double price;
}
