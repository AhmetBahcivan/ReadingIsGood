package com.io.ReadingIsGood.controller;

import com.io.ReadingIsGood.vo.ErrorDetails;
import com.io.ReadingIsGood.vo.GenericResponseErrorItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@RestController
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid is working somehow");
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        List<String> fieldList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField())
                .collect(Collectors.toList());

        ErrorDetails errorDetails;

        if(errorList.contains("must not be blank")) {
            errorDetails = new ErrorDetails("empty_fields", errorList.stream().filter(a -> a.contains("must not be blank")).findAny().get(), fieldList.stream().distinct().collect(Collectors.toList()));
        }
        else if(errorList.contains("size must be between 6 and 40")) {
            errorDetails = new ErrorDetails("invalid_password_pattern", "The password should match with the pattern");
        }
        else if(errorList.contains("size must be between 3 and 50")) {
            errorDetails = new ErrorDetails("invalid_password_pattern", errorList, fieldList);
        }
        else {
            errorDetails = new ErrorDetails("missing_fields", errorList, fieldList );
        }

        GenericResponseErrorItem errorItem = new GenericResponseErrorItem();
        errorItem.setError(errorDetails);
        return handleExceptionInternal(ex, errorItem, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

}
