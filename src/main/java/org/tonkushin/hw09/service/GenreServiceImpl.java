package org.tonkushin.hw09.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.repository.BookRepository;
import org.tonkushin.hw09.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Genre save(Genre item) {
        return repository.save(item);
    }

    @Override
    public Genre findById(String id) throws GenreNotFoundException {
        return repository.findById(id).orElseThrow(GenreNotFoundException::new);
    }

    @Override
    public List<Genre> findAll() {
        return repository.findAllByOrderByName();
    }

    @Override
    public void deleteById(String id) throws GenreHasBooksException {
        if (!bookRepository.existsByGenre_Id(id))
            repository.deleteById(id);
        else
            throw new GenreHasBooksException();
    }
}
