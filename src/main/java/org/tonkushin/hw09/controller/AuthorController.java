package org.tonkushin.hw09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.AuthorService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    static final String AUTHORS_URL = "/authors/";
    static final String EDIT_AUTHORS_URL = "/authors/edit";

    @GetMapping(AUTHORS_URL)
    public String list(Model model) {
        return "authors/list";
    }

    @GetMapping(EDIT_AUTHORS_URL)
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        return "authors/edit";
    }
}
