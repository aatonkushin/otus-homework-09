package org.tonkushin.hw09.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.repository.BookRepository;
import org.tonkushin.hw09.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Genre save(Genre item) throws GenreNotFoundException {
        //Из модели вместо null может прийти пустая строка и тогда MONGO не генерит ID,
        //в этом случае принудительно устанавливаем ID в null
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(null);
            return repository.save(item);
        } else {
            Genre oldItem = repository.findById(item.getId()).orElseThrow(GenreNotFoundException::new);
            oldItem.setName(item.getName());
            return repository.save(item);
        }
    }

    @Override
    public Genre findById(String id) throws GenreNotFoundException {
        return repository.findById(id).orElseThrow(GenreNotFoundException::new);
    }

    @Override
    public List<Genre> findAll() {
        return repository.findAllByOrderByName();
    }

    @Override
    public void deleteById(String id) throws GenreHasBooksException {
        if (!bookRepository.existsByGenre_Id(id))
            repository.deleteById(id);
        else
            throw new GenreHasBooksException();
    }
}
