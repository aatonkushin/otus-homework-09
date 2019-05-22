package org.tonkushin.hw09.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.repository.AuthorRepository;
import org.tonkushin.hw09.repository.BookRepository;
import org.tonkushin.hw09.repository.GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Book save(Book item) throws AuthorNotFoundException, GenreNotFoundException, BookNotFoundException {
        Author a = authorRepository.findById(item.getAuthor().getId()).orElseThrow(AuthorNotFoundException::new);
        Genre g = genreRepository.findById(item.getGenre().getId()).orElseThrow(GenreNotFoundException::new);

        //Из модели вместо null может прийти пустая строка и тогда MONGO не генерит ID,
        //в этом случае принудительно устанавливаем ID в null
        if (item.getId() == null || item.getId().isEmpty()) {
            //Новая запись
            item.setId(null);
            item.setAuthor(a);
            item.setGenre(g);
            return repository.save(item);
        } else {
            //Редактирование записи
            Book oldItem = repository.findById(item.getId()).orElseThrow(BookNotFoundException::new);
            oldItem.setName(item.getName());
            oldItem.setAuthor(a);
            oldItem.setGenre(g);

            return repository.save(oldItem);
        }
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
