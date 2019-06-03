package org.tonkushin.hw09.service.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.repository.BookReactRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Автор: Anton.
 * Дата создания: 02.06.2019
 **/
@Service
public class BookReactServiceImpl implements BookReactService {

    private final BookReactRepository repository;

    @Autowired
    public BookReactServiceImpl(BookReactRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Book> save(Book item) {
        return repository.save(item);
    }

    @Override
    public Mono<Book> findById(String id) throws BookNotFoundException {
        return repository.findById(id).switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @Override
    public Flux<Book> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
