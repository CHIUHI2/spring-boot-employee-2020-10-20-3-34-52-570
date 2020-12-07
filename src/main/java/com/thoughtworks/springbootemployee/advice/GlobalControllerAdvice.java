package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ErrorResponse handleIllegalArgument(IllegalArgumentException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmployeeNotFoundException.class})
    public ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({CompanyNotFoundException.class})
    public ErrorResponse handleCopmanyNotFound(CompanyNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DuplicateKeyException.class})
    public ErrorResponse handleDuplicateKey(DuplicateKeyException exception) {
        return new ErrorResponse(HttpStatus.CONFLICT.name(), exception.getMessage());
    }
}
