package com.io.ReadingIsGood.db.repository;

import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.db.entity.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findById(UUID bookId);
}
