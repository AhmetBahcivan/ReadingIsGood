package com.io.ReadingIsGood.service;

import com.io.ReadingIsGood.db.entity.Book;
import com.io.ReadingIsGood.db.entity.BookOrder;
import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.db.entity.Order;
import com.io.ReadingIsGood.db.repository.BookOrderRepository;
import com.io.ReadingIsGood.db.repository.BookRepository;
import com.io.ReadingIsGood.db.repository.CustomerRepository;
import com.io.ReadingIsGood.db.repository.OrderRepository;
import com.io.ReadingIsGood.security.services.UserDetailsImpl;
import com.io.ReadingIsGood.vo.*;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private BookService bookService;

    public ResponseEntity getOrderById(String orderID) {
        try {
            UUID orderId = UUID.fromString(orderID);

            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if(orderOpt.isEmpty()) {
                log.error("order id: " +orderId+ " not found");
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("order_not_found" ,"order id: " +orderId+ " not found")), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(orderOpt.get(),HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("getOrderById error: " + e.toString());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknown_error" ,e.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    @Synchronized
    public ResponseEntity createOrder(NewOrderItem newOrderItem) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if ( userDetails == null || !userDetails.isAccountNonExpired()) {
                log.error("session user not found ");
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_not_found" ,"session user not found!")), HttpStatus.NOT_FOUND);
            }

            Optional<Customer> customerOpt = customerRepository.findByUsername(userDetails.getUsername());
            if(customerOpt.isEmpty()) {
                log.error("session user not found ");
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_not_found" ,"session user not found!")), HttpStatus.NOT_FOUND);
            }

            Order order = new Order();
            order.setOwner(customerOpt.get());
            order.setCreationDate(new Timestamp(System.currentTimeMillis()));

            double totalPrice = 0;
            List<BookOrderResponseItem> bookOrderResponseItemList = new ArrayList<>();
            for (BookOrderItem bookOrderItem : newOrderItem.getOrderItemList())  {
                Optional<Book> bookOpt = bookRepository.findById(bookOrderItem.getBookId());

                if(bookOpt.isEmpty()) {
                    return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("book_not_found" ,"book id: " + bookOrderItem.getBookId() + " is not found!")), HttpStatus.NOT_FOUND);
                }
                bookOrderResponseItemList.add(new BookOrderResponseItem(bookOpt.get().getId(), bookOpt.get().getName(),bookOrderItem.getCount(),bookOpt.get().getPrice()));
                totalPrice = totalPrice + (bookOpt.get().getPrice() * bookOrderItem.getCount());

                //update book stock
                bookService.updateBookStock(bookOpt.get().getId(), bookOrderItem.getCount());

                BookOrder bookOrder = new BookOrder(bookOrderItem.getCount(), bookOpt.get().getPrice(), bookOpt.get(), order);
                bookOrderRepository.save(bookOrder);
            }
            order.setTotalPrice(totalPrice);
            Order newOrder = orderRepository.save(order);

            OrderResponseItem orderResponseItem = new OrderResponseItem(newOrder.getId(), customerOpt.get(), bookOrderResponseItemList, order.getTotalPrice(), order.getCreationDate());

            customerOpt.get().setOrder_count(customerOpt.get().getOrder_count()+1);
            customerRepository.save(customerOpt.get());

            log.info("Customer : " + userDetails.getUsername() + " ordered " + bookOrderResponseItemList.size() + " number of item on " + order.getCreationDate());
            return new ResponseEntity(orderResponseItem,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Create Order Error : " + e.toString());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknown_error" ,e.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getOrdersByDates(Date startDate, Date endDate) {
        try {
            List<Order> orderList = orderRepository.findAllByCreationDateBetween(startDate, endDate);
            return new ResponseEntity(orderList, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("getOrdersByDates Error : " + e.toString());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknown_error" ,e.toString())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity getMonthlyStatistics() {
        try{
            List<MonthlyStatistic> monthlyStatisticList = orderRepository.getMonthlyOrderStatistic();
            List<OrderStatisticsItem> orderStatisticsItemList = new ArrayList<>();
            for(MonthlyStatistic m: monthlyStatisticList) {
                orderStatisticsItemList.add(new OrderStatisticsItem(m.getTotalOrderCount(),m.getTotalBookCount(),m.getTotalPurchasedAmount(),m.getMonth().trim()));
            }
            return new ResponseEntity(orderStatisticsItemList, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("getMonthlyStatistics Error : " + e.toString());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("unknown_error" ,e.toString())), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
