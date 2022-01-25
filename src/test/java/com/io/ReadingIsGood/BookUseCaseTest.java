package com.io.ReadingIsGood;

import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.service.BookService;
import com.io.ReadingIsGood.vo.BookStockItem;
import com.io.ReadingIsGood.vo.NewBookItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookUseCaseTest {

    @Autowired
    BookService bookService;

    @Test
    void createdBookHasDate() {
        ResponseEntity responseEntity = bookService.createBook(new NewBookItem(null, "bookName", "desc", 150, "authorName", 30, 25.50));
        Book newBook = (Book)responseEntity.getBody();

        assertThat(newBook.getCreatedTime()).isNotNull();
    }

    @Test
    void updatedStockHasCorrectCount() {
        int firstStockCount = 50;
        int purchasedStockCount = 10;

        //creating new book with stock count: 50
        ResponseEntity responseEntity = bookService.createBook(new NewBookItem(null, "bookName", "desc", 150, "authorName", firstStockCount, 25.50));
        Book newBook = (Book)responseEntity.getBody();

        //purchased 10 books
        ResponseEntity newResponseEntity = bookService.updateBookStock(newBook.getId(), purchasedStockCount);
        BookStockItem bookStockItem = (BookStockItem)newResponseEntity.getBody();

        assertThat(newBook.getAvailableCount()-purchasedStockCount).isEqualTo(bookStockItem.getAvailableStock());
    }
}
