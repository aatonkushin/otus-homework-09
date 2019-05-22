package org.tonkushin.hw09.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;

@ControllerAdvice
public class GenreRestControllerAdvice {
    @ResponseBody
    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String authorNotFoundHandler(GenreNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GenreHasBooksException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String authorHasBooksHandler(GenreHasBooksException e){
        return e.getMessage();
    }
}
