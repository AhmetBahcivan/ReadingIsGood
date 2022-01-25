package com.io.ReadingIsGood;

import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.security.jwt.JwtProvider;
import com.io.ReadingIsGood.service.BookService;
import com.io.ReadingIsGood.service.OrderService;
import com.io.ReadingIsGood.vo.NewBookItem;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderUseCaseTest {

    @Autowired
    OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    BookService bookService;

    @org.junit.jupiter.api.Test
    void createdOrderHasRegistrationDate() throws Exception {
        String token = jwtProvider.generateJwtToken(new Customer("ahmet","ahmetbahcivan","ahmetbahcivan@hotmail.com"));

        //create book
        ResponseEntity responseEntityBook = bookService.createBook(new NewBookItem(null, "bookName", "desc", 150, "authorName", 15, 25.50));
        Book newBook = (Book)responseEntityBook.getBody();

        //define order count
        int orderCount = 5;

        //create one book order
        String dataRaw = "{\n" +
                "    \"orderItemList\": [\n" +
                "        {\n" +
                "            \"bookId\":\""+newBook.getId()+"\",\n" +
                "            \"count\":\""+orderCount+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/order/create").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(dataRaw)).andExpect(status().isOk());

    }

    @org.junit.jupiter.api.Test
    void createdOrderHasSameOrderCount() throws Exception {
        String token = jwtProvider.generateJwtToken(new Customer("ahmet","ahmetbahcivan","ahmetbahcivan@hotmail.com"));

        //create book
        ResponseEntity responseEntityBook = bookService.createBook(new NewBookItem(null, "bookName", "desc", 150, "authorName", 15, 25.50));
        Book newBook = (Book)responseEntityBook.getBody();

        //define order count
        int orderCount = 5;

        //create one book order
        String dataRaw = "{\n" +
                "    \"orderItemList\": [\n" +
                "        {\n" +
                "            \"bookId\":\""+newBook.getId()+"\",\n" +
                "            \"count\":\""+orderCount+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mvc.perform(MockMvcRequestBuilders.post("/order/create").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(dataRaw)).andExpect(status().isOk()).andExpect(jsonPath("orderItemList.[0].count", is(orderCount)));

    }
}
