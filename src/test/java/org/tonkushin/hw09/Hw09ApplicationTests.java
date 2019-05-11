package org.tonkushin.hw09;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan({"org.tonkushin.hw09.service", "org.tonkushin.hw09.repository"})
public class Hw09ApplicationTests {

    @Test
    public void contextLoads() {
    }
}