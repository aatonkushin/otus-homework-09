package org.tonkushin.hw09.service.react;

import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorReactService {
    Mono<Author> save(Author item) throws AuthorNotFoundException;

    Mono<Author> findById(String id) throws AuthorNotFoundException;

    Flux<Author> findAll();

    Mono<Void> deleteById(String id) throws AuthorHasBooksException;
}
