package com.io.ReadingIsGood.service;

import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.db.repository.BookRepository;
import com.io.ReadingIsGood.vo.BookStockItem;
import com.io.ReadingIsGood.vo.ErrorDetails;
import com.io.ReadingIsGood.vo.GenericResponseErrorItem;
import com.io.ReadingIsGood.vo.NewBookItem;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity createBook(NewBookItem bookItem) {
        try {
            Book book = new Book(null, bookItem.getName(), bookItem.getDescription(), bookItem.getPageCount(), bookItem.getAuthorName(), bookItem.getAvailableCount(), bookItem.getPrice(), null, new Timestamp(System.currentTimeMillis()), null);
            bookRepository.save(book);
            log.info("book is created. Name: " + book.getName() );
            return new ResponseEntity(book,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("createBook error detail: " + e.getMessage());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknow_error",e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Synchronized
    public ResponseEntity updateBookStock(UUID bookId, int purchasedCount) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isEmpty()) {
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("book_not_found", "Book id : " + bookId + " not found! ")), HttpStatus.NOT_FOUND);
            }
            if (purchasedCount < 1) {
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("purchased_count_error", "Please enter valid purchased count")), HttpStatus.EXPECTATION_FAILED);
            }
            Book book = bookOptional.get();
            if (purchasedCount > book.getAvailableCount()) {
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("out_of_stock", "Please enter max: " + book.getAvailableCount() + " item")), HttpStatus.EXPECTATION_FAILED);
            }

            int newCount = book.getAvailableCount() - purchasedCount;
            book.setAvailableCount(newCount);
            book.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            bookRepository.save(book);
            log.info("book name: " + book.getName() + "'s stock is updated. New available stock: " + book.getAvailableCount());
            BookStockItem bookStockItem = new BookStockItem(book.getId(), book.getName(), book.getAvailableCount());
            return new ResponseEntity(bookStockItem, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("updateBookStock Error: " + e.getMessage());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknown_error", e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
