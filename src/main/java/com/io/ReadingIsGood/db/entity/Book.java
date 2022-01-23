package com.io.ReadingIsGood.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@Entity
@Setter
@Getter
@Table(name="book", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String description;
    private int pageNum;
    private String authorName;
    private int availableCount;
    private double price;
    private Timestamp updateTime;
    private Timestamp createdTime;


    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<BookOrder> bookOrders = new HashSet<>();

    /*
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "book")
    @JsonIgnore
    private Stock stock;

     */

}
