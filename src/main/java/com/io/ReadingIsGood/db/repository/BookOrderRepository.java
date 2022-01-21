package com.io.ReadingIsGood.db.repository;

import com.io.ReadingIsGood.db.entity.BookOrder;
import com.io.ReadingIsGood.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookOrderRepository extends JpaRepository<BookOrder, UUID> {
}
