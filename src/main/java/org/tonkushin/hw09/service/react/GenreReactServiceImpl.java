package org.tonkushin.hw09.service.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.repository.BookReactRepository;
import org.tonkushin.hw09.repository.GenreReactRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenreReactServiceImpl implements GenreReactService {
    private final GenreReactRepository repository;
    private final BookReactRepository bookReactRepository;

    @Autowired
    public GenreReactServiceImpl(GenreReactRepository repository, BookReactRepository bookReactRepository) {
        this.repository = repository;
        this.bookReactRepository = bookReactRepository;
    }


    @Override
    public Mono<Genre> save(Genre item) throws GenreNotFoundException {
        return repository.save(item);
    }

    @Override
    public Mono<Genre> findById(String id) throws GenreNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Flux<Genre> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public Mono<Void> deleteById(String id) throws GenreHasBooksException {
        return bookReactRepository.existsByGenre_Id(id).flatMap(aBoolean ->
                {
                    if (aBoolean == null || !aBoolean) {
                        return repository.deleteById(id);
                    } else {
                        return Mono.error(new GenreHasBooksException());
                    }
                }
        );
    }
}
