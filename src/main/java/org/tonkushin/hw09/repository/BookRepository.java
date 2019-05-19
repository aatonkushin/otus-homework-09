package org.tonkushin.hw09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tonkushin.hw09.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    boolean existsByAuthor_Id(String authorId);
    boolean existsByGenre_Id(String genreId);
    List<Book> findAllByOrderByName();
}
