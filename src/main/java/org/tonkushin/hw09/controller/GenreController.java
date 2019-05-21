package org.tonkushin.hw09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {

    static final String GENRES_URL = "/genres/";
    static final String EDIT_GENRES_URL = "/genres/edit";

    @GetMapping(GENRES_URL)
    public String list(Model model) {
        return "genres/list";
    }

    @GetMapping(EDIT_GENRES_URL)
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        return "genres/edit";
    }
}
