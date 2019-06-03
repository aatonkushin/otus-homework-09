package org.tonkushin.hw09.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.tonkushin.hw09.model.Genre;

public interface GenreReactRepository extends ReactiveMongoRepository<Genre, String> {
}
