package com.io.ReadingIsGood.db.entity;

import com.io.ReadingIsGood.vo.OrderStatisticsItem;
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
@Table(name="order", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_customer_id", nullable = false)
    private Customer owner;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<BookOrder> bookOrderItems = new HashSet<>();

    private double totalPrice;
    private Timestamp creationDate;

    public void addBook(BookOrder bookOrder) {
        bookOrderItems.add(bookOrder);
    }

}
