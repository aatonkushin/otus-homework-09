package org.tonkushin.hw09.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;

@ControllerAdvice
public class AuthorRestControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String authorNotFoundHandler(AuthorNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AuthorHasBooksException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String authorHasBooksHandler(AuthorHasBooksException e){
        return e.getMessage();
    }
}
