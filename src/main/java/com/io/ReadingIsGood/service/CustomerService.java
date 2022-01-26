package com.io.ReadingIsGood.service;

import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.db.entity.Order;
import com.io.ReadingIsGood.db.repository.CustomerRepository;
import com.io.ReadingIsGood.db.repository.OrderRepository;
import com.io.ReadingIsGood.message.JwtResponse;
import com.io.ReadingIsGood.security.jwt.JwtProvider;
import com.io.ReadingIsGood.security.services.UserDetailsImpl;
import com.io.ReadingIsGood.util.OnRegistrationCompleteEvent;
import com.io.ReadingIsGood.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Transactional
    public ResponseEntity registerUser(SignUpForm signUpRequest) {

        List<String> existedFields = new ArrayList<>();

        if(customerRepository.existsByUsername(signUpRequest.getUsername())) {
            existedFields.add("username");
            if(customerRepository.existsByEmail(signUpRequest.getEmail())) {
                existedFields.add("email");
            }
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_already_exists","User already exists", existedFields)), HttpStatus.CONFLICT);
        }
        if(customerRepository.existsByEmail(signUpRequest.getEmail())) {
            existedFields.add("email");
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_already_exists","User already exists", existedFields)), HttpStatus.CONFLICT);
        }

        try {
            // Creating user's account
            Customer user = new Customer(signUpRequest.getName(), signUpRequest.getUsername(),
                    signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

            customerRepository.save(user);


            ResponseEntity responseEntity = authenticateUserAutoLogin(new LoginForm(signUpRequest.getUsername(),signUpRequest.getPassword()));

            return responseEntity;
        }
        catch (Exception e) {
            log.error("register ERROR: " + e.getMessage());
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("internal_error","Registration ERROR: " + e.getMessage(), existedFields)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity authenticateUserAutoLogin(LoginForm loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            Optional<Customer> user = customerRepository.findByUsername(loginRequest.getUsername());
            if(!user.isPresent())
                return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_not_found","User not found! ")), HttpStatus.NOT_FOUND);

            user.get().setLast_login(new Timestamp(System.currentTimeMillis()));
            customerRepository.save(user.get());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(user.get());
            JwtResponse jwtResponse = new JwtResponse(jwt, user.get());

            log.info("user: " +user.get().getUsername() + " is registered succesfully ");

            return ResponseEntity.ok(jwtResponse);

        }
        catch (Exception e) {
            switch (e.getMessage()) {
                case "Bad credentials":
                    return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("invalid_credentials" ,"Wrong username or password")), HttpStatus.UNAUTHORIZED);
                default:
                    return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("login error" ,e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public ResponseEntity<Page<Order>> getAllOrdersFromUser(int pageNum, int limit){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ( userDetails == null || !userDetails.isAccountNonExpired()) {
            log.error("session user not found ");
            return new ResponseEntity(new GenericResponseErrorItem(new ErrorDetails("user_not_found" ,"session user not found!")), HttpStatus.NOT_FOUND);
        }

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "creationDate");
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(order);
        Sort sort = Sort.by(orderList);
        Pageable page = PageRequest.of(pageNum, limit, sort);

        Page<Order> orderPost = orderRepository.findAllByOwnerId(userDetails.getId(), page);
        return ResponseEntity.ok(orderPost);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer user = customerRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return UserPrinciple.build(user);
    }



}
