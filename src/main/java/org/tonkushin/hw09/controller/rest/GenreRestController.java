package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.GenreService;

import java.util.List;

@RestController
public class GenreRestController {

    final static String API_GENRES_URL = "/api/genres/";

    private final GenreService service;

    @Autowired
    public GenreRestController(GenreService service) {
        this.service = service;
    }

    //Возвращает все жанры
    @GetMapping(API_GENRES_URL)
    public List<Genre> getGenres() {
        return service.findAll();
    }

    //Возвращает жанр по id
    @GetMapping(API_GENRES_URL + "/{id}")
    public Genre getGenre(@PathVariable("id") String id) throws GenreNotFoundException {
        Genre item = new Genre();

        if (id != null) {
            //Редактирование существующей записи
            item = service.findById(id);

        }

        return item;
    }

    //Добавляет новый жанр
    @PostMapping(API_GENRES_URL)
    Genre createGenre(@RequestBody Genre item) throws GenreNotFoundException {
        return service.save(item);
    }

    //Редактирует существующий жанр
    @PutMapping(API_GENRES_URL + "/{id}")
    Genre replaceGenre(@RequestBody Genre item, @PathVariable String id) throws GenreNotFoundException {
        return service.save(item);
    }

    //Удаляет жанр по коду
    @DeleteMapping(API_GENRES_URL + "/{id}")
    void deleteGenre(@PathVariable String id) throws GenreHasBooksException {
        service.deleteById(id);
    }
}
