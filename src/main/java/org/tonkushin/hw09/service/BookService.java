package org.tonkushin.hw09.service;

import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book item) throws AuthorNotFoundException, GenreNotFoundException, BookNotFoundException;

    Book findById(String id) throws BookNotFoundException;

    List<Book> findAll();

    void deleteById(String id);
}
