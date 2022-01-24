package com.io.ReadingIsGood.util;


import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.db.repository.CustomerRepository;
import com.io.ReadingIsGood.vo.GenericResponseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class UserHelper {

    public static Customer getHim(CustomerRepository userRepository){

        GenericResponseItem response = new GenericResponseItem();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        log.info("session user username: " + currentUserName);

        Optional<Customer> currentUserOpt;
        Customer user = new Customer();
        user.setUsername(currentUserName);
        try {
            currentUserOpt = userRepository.findByUsername(currentUserName);
            if (currentUserOpt.isPresent()) {
                user = currentUserOpt.get();
                return user;
            }
        }
        catch (Exception e) {
            return user;
        }
        return user;
    }
}
