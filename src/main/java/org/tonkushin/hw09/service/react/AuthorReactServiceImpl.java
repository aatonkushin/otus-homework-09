package org.tonkushin.hw09.service.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.repository.AuthorReactRepository;
import org.tonkushin.hw09.repository.BookReactRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorReactServiceImpl implements AuthorReactService {

    private final AuthorReactRepository repository;
    private final BookReactRepository bookReactRepository;

    @Autowired
    public AuthorReactServiceImpl(AuthorReactRepository repository, BookReactRepository bookReactRepository) {
        this.repository = repository;
        this.bookReactRepository = bookReactRepository;
    }


    @Override
    public Mono<Author> save(Author item) throws AuthorNotFoundException {
        return repository.save(item);
    }

    @Override
    public Mono<Author> findById(String id) throws AuthorNotFoundException {
        return repository.findById(id).switchIfEmpty(Mono.error(new AuthorNotFoundException()));
    }

    @Override
    public Flux<Author> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public Mono<Void> deleteById(String id) throws AuthorHasBooksException {
        return bookReactRepository.existsByAuthor_Id(id).flatMap(aBoolean ->
                {
                    if (aBoolean == null || !aBoolean) {
                        return repository.deleteById(id);
                    } else {
                        return Mono.error(new AuthorHasBooksException());
                    }
                }
        );
    }
}
