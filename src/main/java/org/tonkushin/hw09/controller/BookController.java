package org.tonkushin.hw09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    static final String BOOKS_URL = "/books/";
    static final String EDIT_BOOK_URL = "/books/edit";

    @GetMapping(BOOKS_URL)
    public String list(Model model) {
        return "books/list";
    }

    @GetMapping(EDIT_BOOK_URL)
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        return "books/edit";
    }
}
