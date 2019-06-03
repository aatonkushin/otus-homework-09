package org.tonkushin.hw09.service.react;

import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Автор: Anton.
 * Дата создания: 02.06.2019
 **/
public interface BookReactService {
    Mono<Book> save(Book item);

    Mono<Book> findById(String id) throws BookNotFoundException;

    Flux<Book> findAll();

    Mono<Void> deleteById(String id);
}
