package org.tonkushin.hw09;

import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.tonkushin.hw09.controller.AuthorControllerTest;
import org.tonkushin.hw09.controller.BookControllerTest;
import org.tonkushin.hw09.controller.GenreControllerTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({AuthorControllerTest.class, GenreControllerTest.class, BookControllerTest.class})
public class Hw09ApplicationTests {

    @Test
    public void contextLoads() {
    }
}
