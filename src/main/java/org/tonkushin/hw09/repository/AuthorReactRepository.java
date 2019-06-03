package org.tonkushin.hw09.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.tonkushin.hw09.model.Author;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorReactRepository extends ReactiveMongoRepository<Author, String> {
}
