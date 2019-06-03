package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.react.AuthorReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AuthorReactRestController {

    static final String API_AUTHORS_URL = "/api/v1/authors/";

    private final AuthorReactService service;

    @Autowired
    public AuthorReactRestController(AuthorReactService service) {
        this.service = service;
    }

    @GetMapping(API_AUTHORS_URL)
    public Flux<Author> getAuthors() {
        return service.findAll();
    }

    //Возвращает автора по id
    @GetMapping(API_AUTHORS_URL + "{id}")
    public Mono<Author> getAuthor(@PathVariable("id") String id) throws AuthorNotFoundException {
        return service.findById(id);
    }

    //Добавляет нового автора
    @PostMapping(API_AUTHORS_URL)
    Mono<Author> createAuthor(@RequestBody Author item) throws AuthorNotFoundException {
        return service.save(item);
    }

    //Редактирует существующего автора
    @PutMapping(API_AUTHORS_URL + "{id}")
    Mono<Author> replaceAuthor(@RequestBody Author item, @PathVariable String id) throws AuthorNotFoundException {
        return service.save(item);
    }

    //Удаляет автора
    @DeleteMapping(API_AUTHORS_URL + "{id}")
    Mono<Void> deleteAuthor(@PathVariable String id) throws AuthorHasBooksException {
        return service.deleteById(id);
    }
}
