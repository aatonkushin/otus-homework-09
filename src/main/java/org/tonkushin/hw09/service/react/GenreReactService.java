package org.tonkushin.hw09.service.react;

import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreReactService {
    Mono<Genre> save(Genre item) throws GenreNotFoundException;

    Mono<Genre> findById(String id) throws GenreNotFoundException;

    Flux<Genre> findAll();

    Mono<Void> deleteById(String id) throws GenreHasBooksException;
}
