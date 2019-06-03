package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.service.react.BookReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Автор: Anton.
 * Дата создания: 02.06.2019
 **/
@RestController
public class BookReactRestController {
    static final String API_BOOKS_URL = "/api/v1/books/";

    private final BookReactService service;

    @Autowired
    public BookReactRestController(BookReactService service) {
        this.service = service;
    }

    @GetMapping(API_BOOKS_URL)
    public Flux<Book> getBooks() {
        return service.findAll();
    }

    //Возвращает книгу по id
    @GetMapping(API_BOOKS_URL + "{id}")
    public Mono<Book> getBook(@PathVariable("id") String id) throws BookNotFoundException {
        return service.findById(id);
    }

    //Добавляет новую книгу
    @PostMapping(API_BOOKS_URL)
    Mono<Book> createBook(@RequestBody Book item) throws BookNotFoundException {
        return service.save(item);
    }

    //Редактирует существующую книгу
    @PutMapping(API_BOOKS_URL + "{id}")
    Mono<Book> replaceBook(@RequestBody Book item, @PathVariable String id) throws BookNotFoundException {
        return service.save(item);
    }

    //Удаляет книгу
    @DeleteMapping(API_BOOKS_URL + "{id}")
    Mono<Void> deleteBook(@PathVariable String id) {
        return service.deleteById(id);
    }
}
