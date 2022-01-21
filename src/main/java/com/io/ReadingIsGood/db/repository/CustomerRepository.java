package com.io.ReadingIsGood.db.repository;

import com.io.ReadingIsGood.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {


    @Query("Select u from Customer u where u.username =  ?1 ")
    Optional<Customer> findByUsername(String username);

}
