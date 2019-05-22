package org.tonkushin.hw09.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreRestController.class)
public class GenreRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    private static final String TEST_GENRE_1 = "Тестовый жанр 1";
    private static final String TEST_GENRE_2 = "Тестовый жанр 2";


    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовый жанр 1 и Тестовый жанр 2")
    void getGenres() throws Exception {
        Mockito.when(genreService.findAll()).thenReturn(new ArrayList<Genre>() {{
            add(new Genre(TEST_GENRE_1));
            add(new Genre(TEST_GENRE_2));
        }});

        MvcResult mvcResult = mvc.perform(get(GenreRestController.API_GENRES_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).contains(TEST_GENRE_1);
        Assertions.assertThat(content).contains(TEST_GENRE_2);
    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовый жанр 1")
    void getGenre() throws Exception {
        Mockito.when(genreService.findById("1")).thenReturn(new Genre(TEST_GENRE_1));

        MvcResult mvcResult = mvc.perform(get(GenreRestController.API_GENRES_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_GENRE_1);
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовый жанр 1")
    void createGenre() throws Exception {
        Genre item = new Genre(TEST_GENRE_1);
        Mockito.when(genreService.save(item)).thenReturn(item);

        String requestJson = getJsonString(item);

        MvcResult mvcResult = mvc.perform(post(GenreRestController.API_GENRES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_GENRE_1);
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовый жанр 1")
    void replaceGenre() throws Exception {
        Genre item = new Genre(TEST_GENRE_1);
        Mockito.when(genreService.save(item)).thenReturn(item);

        String requestJson = getJsonString(item);

        MvcResult mvcResult = mvc.perform(post(GenreRestController.API_GENRES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_GENRE_1);
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteGenre() throws Exception {
        mvc.perform(delete(GenreRestController.API_GENRES_URL + "/1")).andExpect(status().isOk());
    }

    private String getJsonString(Genre item) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(item);
    }
}
