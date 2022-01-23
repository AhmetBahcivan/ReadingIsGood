package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.service.BookService;
import com.io.ReadingIsGood.vo.NewBookItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity createNewBook(@Valid @RequestBody NewBookItem bookItem){
        return bookService.createBook(bookItem);
    }

    @PostMapping("/updateStock")
    public ResponseEntity updateBookStock(UUID bookId, int purchasedCount){
        return bookService.updateBookStock(bookId, purchasedCount);
    }

}
