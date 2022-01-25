package com.io.ReadingIsGood;


import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.message.JwtResponse;
import com.io.ReadingIsGood.security.jwt.JwtProvider;
import com.io.ReadingIsGood.service.BookService;
import com.io.ReadingIsGood.service.CustomerService;
import com.io.ReadingIsGood.service.OrderService;
import com.io.ReadingIsGood.vo.*;
import org.hamcrest.Matcher;
import org.hibernate.validator.constraints.Range;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.hamcrest.Matchers.*;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterCustomerUseCaseTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Test
    void savedCustomerHasRegistrationDateOrError() {
        ResponseEntity<Object> responseEntity = customerService.registerUser(new SignUpForm("ahmet", "ahmetbahcivan", "pass123Z?", "ahmetbahcivannnn@hotmail.com"));
        //first time run
        //success
        if(responseEntity.getBody().getClass().getName().equals(JwtResponse.class)) {
            JwtResponse jwtResponse = (JwtResponse)responseEntity.getBody();
            Customer newCustomer = jwtResponse.getCustomer();
            assertThat(newCustomer.getJoined_on()).isNotNull();

        }
        //after first time
        //username already exists error
        else if(responseEntity.getBody().getClass().getName().equals(GenericResponseErrorItem.class)) {
            GenericResponseErrorItem genericResponseErrorItem = (GenericResponseErrorItem)responseEntity.getBody();
            assertThat(genericResponseErrorItem.getError().getCode()).isEqualTo("user_already_exists");
        }
    }






}
