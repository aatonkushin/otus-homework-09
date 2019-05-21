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
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.BookService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService service;

    @Test
    @DisplayName("Получить все: Должен вернуть JSON с Тестовая книга")
    void getBooks() throws Exception {
        Book b = getTestBook();
        Mockito.when(service.findAll()).thenReturn(new ArrayList<Book>() {{
            add(b);
            add(b);
        }});

        MvcResult mvcResult = mvc.perform(get(BookRestController.API_BOOKS_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).contains(b.getName());
    }

    @Test
    @DisplayName("Получить по коду: Должен вернуть JSON с Тестовая книга")
    void getBook() throws Exception {
        Book b = getTestBook();
        Mockito.when(service.findById("1")).thenReturn(b);

        MvcResult mvcResult = mvc.perform(get(BookRestController.API_BOOKS_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).contains(b.getName());
    }

    @Test
    @DisplayName("Сохранить: Должен вернуть JSON с Тестовая книга")
    void createBook() throws Exception {
        Book b = getTestBook();
        Mockito.when(service.save(b)).thenReturn(b);

        String requestJson = getJsonString(b);

        MvcResult mvcResult = mvc.perform(post(BookRestController.API_BOOKS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(b.getName());
    }

    @Test
    @DisplayName("Изменить: Должен вернуть JSON с Тестовая книга")
    void replaceBook() throws Exception {
        Book b = getTestBook();
        Mockito.when(service.save(b)).thenReturn(b);

        String requestJson = getJsonString(b);

        MvcResult mvcResult = mvc.perform(put(BookRestController.API_BOOKS_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(b.getName());
    }

    @Test
    @DisplayName("Удалить: должен вернуть ОК")
    void deleteBook() throws Exception {
        mvc.perform(delete(BookRestController.API_BOOKS_URL + "/1")).andExpect(status().isOk());
    }

    private Book getTestBook(){
        Author author = new Author("Тестовый автор");
        Genre genre = new Genre("Тестовый жанр");
        return new Book("Тестовая книга", genre, author);
    }

    private String getJsonString(Book item) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(item);
    }
}
