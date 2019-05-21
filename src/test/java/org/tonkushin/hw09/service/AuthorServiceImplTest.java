package org.tonkushin.hw09.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.repository.AuthorRepository;
import org.tonkushin.hw09.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository repository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Проверка метода сохранения записи в БД")
    void save() throws AuthorNotFoundException {
        AuthorServiceImpl service = new AuthorServiceImpl(repository, bookRepository);
        Author itemToSave = new Author("Пушкин");

        BDDMockito.given(repository.save(itemToSave)).willReturn(itemToSave);
        Author savedItem = service.save(itemToSave);

        Assertions.assertThat(itemToSave.getName().equals(savedItem.getName()));
    }

    @Test
    @DisplayName("Проверка метода поиска по ID")
    void findById() throws AuthorNotFoundException {
        AuthorServiceImpl service = new AuthorServiceImpl(repository, bookRepository);
        Author item = new Author("Пушкин");
        BDDMockito.given(repository.findById("1")).willReturn(java.util.Optional.of(item));
        Author found = service.findById("1");

        Assertions.assertThat(item.getName().equals(found.getName()));
    }

    @Test
    @DisplayName("Проверка метода поиска всех записей")
    void findAll() {
        AuthorServiceImpl service = new AuthorServiceImpl(repository, bookRepository);
        List<Author> items = new ArrayList<>(3);
        items.add(new Author("Пушкин"));
        items.add(new Author("Лермонтов"));
        items.add(new Author("Толстой"));

        BDDMockito.given(repository.findAllByOrderByName()).willReturn(items);

        Assertions.assertThat(service.findAll().size()).isEqualTo(3);

    }
}
