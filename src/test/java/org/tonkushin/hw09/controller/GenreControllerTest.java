package org.tonkushin.hw09.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
@ComponentScan({"org.tonkushin.hw09.controller", "org.tonkushin.hw09.service", "org.tonkushin.hw09.repository"})
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Должен вернуть страницу с текстом ЖАНРЫ")
    void shouldContainGenres() throws Exception {
        mvc.perform(get("/genres/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string("ЖАНРЫ"));
    }

    @Test
    void edit() {
    }
}
