package org.tonkushin.hw09.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.GenreService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    private static final String TEST_GENRE = "Тестовый жанр";

    private static final String GENRES = "ЖАНРЫ";

    private static final String CREATE_GENRE = "Создание жанра";

    @Test
    @DisplayName("Должен вернуть страницу с текстом ЖАНРЫ и запись Тестовый жанр")
    void shouldContainGenres() throws Exception {

        //Для изучения работы @MockBean
        Mockito.when(genreService.findAll()).thenReturn(new ArrayList<Genre>() {{
            add(new Genre(TEST_GENRE));
        }});

        MvcResult mvcResult = mvc.perform(get("/genres/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(GENRES);
        Assertions.assertThat(content).contains(TEST_GENRE);
    }

    @Test
    @DisplayName("Должен вернуть страницу с текстом Создание жанра")
    void shouldReturnCreateGenre() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/genres/edit").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(CREATE_GENRE);
    }
}
