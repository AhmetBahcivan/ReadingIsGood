package com.io.ReadingIsGood.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ErrorDetails {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String code;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> messages;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> fields;


    public ErrorDetails(String code, List<String> messages, List<String> errors, List<String> fields) {
        super();
        this.code = code;
        this.messages = messages;
        this.errors = errors;
        this.fields = fields;

    }

    public ErrorDetails(String code, List<String> messages, List<String> fields) {
        super();
        this.code = code;
        this.messages = messages;
        this.fields = fields;

    }

    public ErrorDetails(String code, String message, List<String> fields) {
        super();
        this.code = code;
        this.messages = new ArrayList<>();
        this.messages.add(message);
        this.fields = fields;
    }

    public ErrorDetails(String code, String message) {
        super();
        this.code = code;
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public ErrorDetails(String code, String message, String error, String field) {
        super();
        this.code = code;
        this.messages = messages;
        errors = Arrays.asList(error);
        fields = Arrays.asList(field);
    }
}
