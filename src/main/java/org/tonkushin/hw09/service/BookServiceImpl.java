package org.tonkushin.hw09.service;

import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book item) {
        return repository.save(item);
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        return repository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAllByOrderByName();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
