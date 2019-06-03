package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.react.GenreReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GenreReactRestController {
    static final String API_GENRES_URL = "/api/v1/genres/";

    private final GenreReactService service;

    @Autowired
    public GenreReactRestController(GenreReactService service) {
        this.service = service;
    }

    @GetMapping(API_GENRES_URL)
    public Flux<Genre> getGenres() {
        return service.findAll();
    }

    //Возвращает жанр по id
    @GetMapping(API_GENRES_URL + "{id}")
    public Mono<Genre> getGenre(@PathVariable("id") String id) throws GenreNotFoundException {
        return service.findById(id).switchIfEmpty(Mono.error(new GenreNotFoundException()));
    }

    //Добавляет новый жанр
    @PostMapping(API_GENRES_URL)
    Mono<Genre> createGenre(@RequestBody Genre item) throws GenreNotFoundException {
        return service.save(item);
    }

    //Редактирует существующий жанр
    @PutMapping(API_GENRES_URL + "{id}")
    Mono<Genre> replaceGenre(@RequestBody Genre item, @PathVariable String id) throws GenreNotFoundException {
        return service.save(item);
    }

    //Удаляет жанр
    @DeleteMapping(API_GENRES_URL + "{id}")
    Mono<Void> deleteGenre(@PathVariable String id) throws GenreHasBooksException {
        return service.deleteById(id);
    }
}
