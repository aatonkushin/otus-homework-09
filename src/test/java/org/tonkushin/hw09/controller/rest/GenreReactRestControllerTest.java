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
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.react.GenreReactService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Жанр: Anton.
 * Дата создания: 02.06.2019
 **/
@WebFluxTest(GenreReactRestController.class)
public class GenreReactRestControllerTest {
    @MockBean
    private GenreReactService authorService;

    @Autowired
    private WebTestClient webClient;

    private static final String TEST_GENRE_1 = "Тестовый жанр 1";
    private static final String TEST_GENRE_2 = "Тестовый жанр 2";

    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовый жанр 1 и Тестовый жанр 2")
    void getGenres() {
        List<Genre> items = new ArrayList<Genre>() {{
            add(new Genre(TEST_GENRE_1));
            add(new Genre(TEST_GENRE_2));
        }};
        Flux<Genre> flux = Flux.fromIterable(items);
        Mockito.when(authorService.findAll()).thenReturn(flux);

        webClient.get().uri(GenreReactRestController.API_GENRES_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk().expectBodyList(Genre.class).contains(items.get(0), items.get(1));
    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовый жанр 1")
    void getGenre() throws Exception {
        Genre item = new Genre(TEST_GENRE_1);
        Mono<Genre> mono = Mono.just(item);
        Mockito.when(authorService.findById("1")).thenReturn(mono);

        webClient.get().uri(GenreReactRestController.API_GENRES_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовый жанр 1")
    void createGenre() throws Exception {
        Genre item = new Genre(TEST_GENRE_1);
        Mono<Genre> mono = Mono.just(item);
        Mockito.when(authorService.save(item)).thenReturn(mono);

        webClient.post().uri(GenreReactRestController.API_GENRES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Genre.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовый жанр 1")
    void replaceGenre() throws Exception {
        Genre item = new Genre(TEST_GENRE_1);
        Mono<Genre> mono = Mono.just(item);
        Mockito.when(authorService.save(item)).thenReturn(mono);

        webClient.put().uri(GenreReactRestController.API_GENRES_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(item))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Genre.class)
                .isEqualTo(item);
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteGenre() {
        webClient.delete().uri(GenreReactRestController.API_GENRES_URL + "1").exchange().expectStatus().isOk();
    }
}
