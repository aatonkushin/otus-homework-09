package org.tonkushin.hw09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tonkushin.hw09.model.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findAllByOrderByName();
}
