package org.tonkushin.hw09.service;

import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book item) {
        //Из модели вместо null может прийти пустая строка и тогда MONGO не генерит ID,
        //в этом случае принудительно устанавливаем ID в null
        if (item.getId() != null && item.getId().isEmpty())
            item.setId(null);

        return repository.save(item);
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        return repository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAllByOrderByName();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
