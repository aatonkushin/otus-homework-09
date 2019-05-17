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
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.AuthorService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    private static final String TEST_AUTHOR = "Тестовый автор";

    private static final String AUTHORS = "АВТОРЫ";

    private static final String CREATE_AUTHOR = "Создание автора";

    @Test
    @DisplayName("Должен вернуть страницу с текстом АВТОРЫ и запись Тестовый автор")
    void shouldContainAuthors() throws Exception {
        //Для изучения работы @MockBean
        Mockito.when(authorService.findAll()).thenReturn(new ArrayList<Author>() {{
            add(new Author(TEST_AUTHOR));
        }});

        MvcResult mvcResult = mvc.perform(get("/authors/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(AUTHORS);
        Assertions.assertThat(content).contains(TEST_AUTHOR);
    }

    @Test
    @DisplayName("Должен вернуть страницу с текстом Создание автора")
    void shouldContainEditAuthor() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/authors/edit").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(content).contains(CREATE_AUTHOR);
    }
}
