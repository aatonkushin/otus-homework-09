package org.tonkushin.hw09.service;

import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.repository.AuthorRepository;
import org.tonkushin.hw09.repository.BookRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author save(Author item) {
        return repository.save(item);
    }

    @Override
    public Author findById(String id) throws AuthorNotFoundException {
        return repository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    @Override
    public List<Author> findAll() {
        return repository.findAllByOrderByName();
    }

    @Override
    public void deleteById(String id) throws AuthorHasBooksException {
        if (!bookRepository.existsByAuthor_Id(id))
            repository.deleteById(id);
        else
            throw new AuthorHasBooksException();
    }
}
