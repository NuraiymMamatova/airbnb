package com.example.airbnbb7.exceptions.handler;

import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.ExceptionResponse;
import com.example.airbnbb7.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerNotFoundException(BadCredentialsException e) {

        return new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                e.getClass().getSimpleName(),
                e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerNotFoundException(BadRequestException e) {

        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage());
    }

}