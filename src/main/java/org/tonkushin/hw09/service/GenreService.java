package org.tonkushin.hw09.service;

import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;

import java.util.List;

public interface GenreService {
    Genre save(Genre item) throws GenreNotFoundException;

    Genre findById(String id) throws GenreNotFoundException;

    List<Genre> findAll();

    void deleteById(String id) throws GenreHasBooksException;
}
