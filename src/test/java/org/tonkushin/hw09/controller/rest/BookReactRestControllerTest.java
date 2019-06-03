package org.tonkushin.hw09.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.react.BookReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: Anton.
 * Дата создания: 02.06.2019
 **/
@WebFluxTest(BookReactRestController.class)
public class BookReactRestControllerTest {
    @MockBean
    private BookReactService service;

    @Autowired
    private WebTestClient webClient;

    private Book getTestBook() {
        Author author = new Author("Тестовый автор");
        Genre genre = new Genre("Тестовый жанр");
        return new Book("Тестовая книга", genre, author);
    }

    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовая книга")
    void getBooks() throws Exception {
        Book b = getTestBook();
        List<Book> items = new ArrayList<Book>() {{
            add(b);
            add(b);
        }};
        Flux<Book> flux = Flux.fromIterable(items);
        Mockito.when(service.findAll()).thenReturn(flux);

        webClient.get().uri(BookReactRestController.API_BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .contains(items.get(0), items.get(1));

    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовая книга")
    void getBook() throws Exception {
        Book item = getTestBook();
        Mono<Book> mono = Mono.just(item);
        Mockito.when(service.findById("1")).thenReturn(mono);

        webClient.get().uri(BookReactRestController.API_BOOKS_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовая книга")
    void createBook() throws Exception {
        Book item = getTestBook();
        Mono<Book> mono = Mono.just(item);
        Mockito.when(service.save(item)).thenReturn(mono);

        webClient.post().uri(BookReactRestController.API_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовая книга")
    void replaceBook() throws Exception {
        Book item = getTestBook();
        Mono<Book> mono = Mono.just(item);
        Mockito.when(service.save(item)).thenReturn(mono);

        webClient.put().uri(BookReactRestController.API_BOOKS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteBook() {
        webClient.delete().uri(BookReactRestController.API_BOOKS_URL + "1").exchange().expectStatus().isOk();
    }
}
