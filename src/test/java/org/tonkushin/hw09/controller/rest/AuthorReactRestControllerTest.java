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
import org.tonkushin.hw09.service.react.AuthorReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Автор: Anton.
 * Дата создания: 02.06.2019
 **/
@WebFluxTest(AuthorReactRestController.class)
public class AuthorReactRestControllerTest {

    @MockBean
    private AuthorReactService authorService;

    @Autowired
    private WebTestClient webClient;

    private static final String TEST_AUTHOR_1 = "Тестовый автор 1";
    private static final String TEST_AUTHOR_2 = "Тестовый автор 2";

    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовый автор 1 и Тестовый автор 2")
    void getAuthors() {
        List<Author> items = new ArrayList<Author>() {{
            add(new Author(TEST_AUTHOR_1));
            add(new Author(TEST_AUTHOR_2));
        }};
        Flux<Author> flux = Flux.fromIterable(items);
        Mockito.when(authorService.findAll()).thenReturn(flux);

        webClient.get().uri(AuthorReactRestController.API_AUTHORS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk().expectBodyList(Author.class).contains(items.get(0), items.get(1));
    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовый автор 1")
    void getAuthor() throws Exception {
        Author item = new Author(TEST_AUTHOR_1);
        Mono<Author> mono = Mono.just(item);
        Mockito.when(authorService.findById("1")).thenReturn(mono);

        webClient.get().uri(AuthorReactRestController.API_AUTHORS_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Author.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовый автор 1")
    void createAuthor() throws Exception {
        Author item = new Author(TEST_AUTHOR_1);
        Mono<Author> mono = Mono.just(item);
        Mockito.when(authorService.save(item)).thenReturn(mono);

        webClient.post().uri(AuthorReactRestController.API_AUTHORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовый автор 1")
    void replaceAuthor() throws Exception {
        Author item = new Author(TEST_AUTHOR_1);
        Mono<Author> mono = Mono.just(item);
        Mockito.when(authorService.save(item)).thenReturn(mono);

        webClient.put().uri(AuthorReactRestController.API_AUTHORS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Author.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteAuthor() {
        webClient.delete().uri(AuthorReactRestController.API_AUTHORS_URL + "1").exchange().expectStatus().isOk();
    }
}
