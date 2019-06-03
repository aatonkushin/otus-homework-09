package org.tonkushin.hw09.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.tonkushin.hw09.model.Book;
import reactor.core.publisher.Mono;

public interface BookReactRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Boolean> existsByAuthor_Id(String authorId);
    Mono<Boolean> existsByGenre_Id(String genreId);
}
