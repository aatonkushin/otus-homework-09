package org.tonkushin.hw09.service;

import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author item);

    Author findById(String id) throws AuthorNotFoundException;

    List<Author> findAll();

    void deleteById(String id) throws AuthorHasBooksException;
}
