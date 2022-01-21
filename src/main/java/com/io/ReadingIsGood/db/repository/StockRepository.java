package com.io.ReadingIsGood.db.repository;

import com.io.ReadingIsGood.db.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
}
