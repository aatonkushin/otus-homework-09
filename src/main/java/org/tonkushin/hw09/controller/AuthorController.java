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

    private final AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/authors/")
    public String list(Model model) {
        model.addAttribute("items", service.findAll());
        return "authors/list";
    }

    @GetMapping("/authors/edit")
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        Author item = new Author();

        //Создание новой записи
        if (id != null) {
            //Редактирование существующей записи
            try {
                item = service.findById(id);
            } catch (AuthorNotFoundException e) {
                e.printStackTrace();
                model.addAttribute("errors", new ArrayList<String>() {{
                    add(String.format("Автор с кодом %s не найден", id));
                }});
            }
        }
        model.addAttribute("item", item);
        return "authors/edit";
    }

    @PostMapping("/authors/edit")
    public String edit(Model model, @Valid Author item, BindingResult bindingResult) {
        // Проверяем ошибки.
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors",
                    bindingResult.getFieldErrors()
                            .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));

            model.addAttribute("item", item);

            return "authors/edit";
        }

        try {
            service.save(item);
        } catch (Exception e) {
            model.addAttribute("errors", new ArrayList<String>() {{
                add(e.getMessage());
            }});
            model.addAttribute("item", item);
            return "authors/edit";
        }

        return "redirect:/authors/";
    }

    @GetMapping("/authors/delete")
    public String delete(Model model, @RequestParam(value = "id") String id) {
        return deleteAuthor(model, id);
    }
    @DeleteMapping("/authors/delete")
    public String deleteAlt(Model model, @RequestParam(value = "id") String id) {
        return deleteAuthor(model, id);
    }

    //Удаляет автора из БД
    private String deleteAuthor(Model model, @RequestParam("id") String id) {
        try {
            service.deleteById(id);
            return "redirect:/authors/";
        } catch (AuthorHasBooksException e) {
            e.printStackTrace();
            List<String> errors = new ArrayList<>();
            errors.add("Невозможно удалить автора, т.к. у него есть книги.");
            model.addAttribute("errors", errors);

            try {
                model.addAttribute("item", service.findById(id));
            } catch (AuthorNotFoundException ex) {
                errors.add(String.format("Автор с кодом %s не найден.", id));
                model.addAttribute("errors", errors);
                ex.printStackTrace();
            }

            return "authors/edit";
        }
    }
}
