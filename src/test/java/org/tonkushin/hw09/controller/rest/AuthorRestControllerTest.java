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
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.AuthorService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    private static final String TEST_AUTHOR_1 = "Тестовый автор 1";
    private static final String TEST_AUTHOR_2 = "Тестовый автор 2";

    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовый автор 1 и Тестовый автор 2")
    void getAuthors() throws Exception {
        Mockito.when(authorService.findAll()).thenReturn(new ArrayList<Author>() {{
            add(new Author(TEST_AUTHOR_1));
            add(new Author(TEST_AUTHOR_2));
        }});

        MvcResult mvcResult = mvc.perform(get(AuthorRestController.API_AUTHORS_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).contains(TEST_AUTHOR_1);
        Assertions.assertThat(content).contains(TEST_AUTHOR_2);
    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовый автор 1")
    void getAuthor() throws Exception {
        Mockito.when(authorService.findById("1")).thenReturn(new Author(TEST_AUTHOR_1));

        MvcResult mvcResult = mvc.perform(get(AuthorRestController.API_AUTHORS_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_AUTHOR_1);
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовый автор 1")
    void createAuthor() throws Exception {
        Author item = new Author(TEST_AUTHOR_1);
        Mockito.when(authorService.save(item)).thenReturn(item);

        String requestJson = getJsonString(item);

        MvcResult mvcResult = mvc.perform(post(AuthorRestController.API_AUTHORS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_AUTHOR_1);
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовый автор 1")
    void replaceAuthor() throws Exception {
        Author item = new Author(TEST_AUTHOR_1);
        Mockito.when(authorService.save(item)).thenReturn(item);

        String requestJson = getJsonString(item);

        MvcResult mvcResult = mvc.perform(put(AuthorRestController.API_AUTHORS_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(TEST_AUTHOR_1);
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteAuthor() throws Exception {
        mvc.perform(delete(AuthorRestController.API_AUTHORS_URL + "/1")).andExpect(status().isOk());
    }

    private String getJsonString(Author item) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(item);
    }
}
