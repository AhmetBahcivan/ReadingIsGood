package com.io.ReadingIsGood.util;

import com.io.ReadingIsGood.db.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Setter
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Customer customer;

    public OnRegistrationCompleteEvent(
            Customer customer, Locale locale, String appUrl) {
        super(customer);
        this.customer = customer;
        this.locale = locale;
        this.appUrl = appUrl;
    }
    // standard getters and setters
}

