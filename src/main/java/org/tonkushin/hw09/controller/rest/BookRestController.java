package org.tonkushin.hw09.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.service.BookService;

import java.util.List;

@RestController
public class BookRestController {
    final static String API_BOOKS_URL = "/api/books/";

    private final BookService service;

    @Autowired
    public BookRestController(BookService service) {
        this.service = service;
    }

    //Возвращает все книги
    @GetMapping(API_BOOKS_URL)
    public List<Book> getBooks() {
        return service.findAll();
    }

    //Возвращает книгу по id
    @GetMapping(API_BOOKS_URL + "/{id}")
    public Book getBook(@PathVariable("id") String id) throws BookNotFoundException {
        Book item = new Book();

        if (id != null) {
            //Редактирование существующей записи
            item = service.findById(id);

        }

        return item;
    }

    //Добавляет новую книгу
    @PostMapping(API_BOOKS_URL)
    Book createBook(@RequestBody Book item) throws BookNotFoundException, GenreNotFoundException, AuthorNotFoundException {
        return service.save(item);
    }

    //Редактирует существующую книгу
    @PutMapping(API_BOOKS_URL + "/{id}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable String id) throws BookNotFoundException, AuthorNotFoundException, GenreNotFoundException {
        return service.save(newBook);
    }

    //Удаляет книгу по коду
    @DeleteMapping(API_BOOKS_URL + "/{id}")
    void deleteBook(@PathVariable String id) {
        service.deleteById(id);
    }
}
