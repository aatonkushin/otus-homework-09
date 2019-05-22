package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.AuthorService;

import java.util.List;

@RestController
public class AuthorRestController {

    static final String API_AUTHORS_URL = "/api/authors/";


    private final AuthorService service;

    @Autowired
    public AuthorRestController(AuthorService service) {
        this.service = service;
    }

    //Возвращает всех авторов
    @GetMapping(API_AUTHORS_URL)
    public List<Author> getAuthors() {
        return service.findAll();
    }

    //Возвращает автора по id
    @GetMapping(API_AUTHORS_URL + "{id}")
    public Author getAuthor(@PathVariable("id") String id) throws AuthorNotFoundException {
        Author item = new Author();

        if (id != null) {
            //Редактирование существующей записи
            item = service.findById(id);

        }

        return item;
    }

    //Добавляет нового автора
    @PostMapping(API_AUTHORS_URL)
    Author createAuthor(@RequestBody Author item) throws AuthorNotFoundException {
        return service.save(item);
    }

    //Редактирует существующего автора
    @PutMapping(API_AUTHORS_URL + "{id}")
    Author replaceAuthor(@RequestBody Author item, @PathVariable String id) throws AuthorNotFoundException {
        return service.save(item);
    }

    //Удаляет автора
    @DeleteMapping(API_AUTHORS_URL + "{id}")
    void deleteAuthor(@PathVariable String id) throws AuthorHasBooksException {
        service.deleteById(id);
    }
}
