package org.tonkushin.hw09.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldContainAuthors() {
        Assertions.assertThat(this.restTemplate
                .getForObject("http://localhost:" + port + "/authors/", String.class))
                .contains("АВТОРЫ");
    }

    @Test
    void shouldContainEditAuthor(){
        Assertions.assertThat(this.restTemplate
                .getForObject("http://localhost:" + port + "/authors/edit", String.class))
                .contains("Создание автора");
    }
}
