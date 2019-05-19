package org.tonkushin.hw09.service;

import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book item);

    Book findById(String id) throws BookNotFoundException;

    List<Book> findAll();

    void deleteById(String id);
}
