package org.tonkushin.hw09.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.tonkushin.hw09.service.AuthorService;
import org.tonkushin.hw09.service.BookService;
import org.tonkushin.hw09.service.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static final String BOOKS = "КНИГИ";

    private static final String CREATE_BOOK = "Создание книги";


    @Test
    @DisplayName("Должен вернуть страницу с текстом КНИГИ")
    void shouldReturnBooks() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/books/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(BOOKS);
    }

    @Test
    @DisplayName("Должен вернуть страницу с текстом Создание книги")
    void shouldReturnCreateBook() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/books/edit").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(CREATE_BOOK);
    }
}
