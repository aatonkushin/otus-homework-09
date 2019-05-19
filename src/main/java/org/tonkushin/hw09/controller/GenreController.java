package org.tonkushin.hw09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.GenreService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreController {
    private final GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/genres/")
    public String list(Model model) {
        model.addAttribute("items", service.findAll());
        return "genres/list";
    }

    @GetMapping("/genres/edit")
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        Genre item = new Genre();

        //Создание новой записи
        if (id != null) {
            //Редактирование существующей записи
            try {
                item = service.findById(id);
            } catch (GenreNotFoundException e) {
                e.printStackTrace();
                model.addAttribute("errors", new ArrayList<String>() {{
                    add(String.format("Жанр с кодом %s не найден", id));
                }});
            }
        }
        model.addAttribute("item", item);
        return "genres/edit";
    }

    @PostMapping("/genres/edit")
    public String edit(Model model, @Valid Genre item, BindingResult bindingResult) {
        // Проверяем ошибки.
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors",
                    bindingResult.getFieldErrors()
                            .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));

            model.addAttribute("item", item);

            return "genres/edit";
        }

        try {
            service.save(item);
        } catch (Exception e) {
            model.addAttribute("errors", new ArrayList<String>() {{
                add(e.getMessage());
            }});
            model.addAttribute("item", item);
            return "genres/edit";
        }

        return "redirect:/genres/";
    }

    @GetMapping("/genres/delete")
    public String delete(Model model, @RequestParam(value = "id") String id) {
        try {
            service.deleteById(id);
            return "redirect:/genres/";
        } catch (GenreHasBooksException e) {
            e.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add("Невозможно удалить жанр, т.к. у него есть книги.");
            model.addAttribute("errors", errors);

            try {
                model.addAttribute("item", service.findById(id));
            } catch (GenreNotFoundException ex) {
                errors.add(String.format("Жанр с кодом %s не найден.", id));
                model.addAttribute("errors", errors);
                ex.printStackTrace();
            }

            return "genres/edit";
        }
    }
}
