package org.tonkushin.hw09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tonkushin.hw09.exception.AuthorHasBooksException;
import org.tonkushin.hw09.exception.AuthorNotFoundException;
import org.tonkushin.hw09.model.Author;
import org.tonkushin.hw09.service.AuthorService;

import javax.validation.Valid;

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
                model.addAttribute("error", String.format("Автор с кодом %s не найден", id));
            }
        }
        model.addAttribute("item", item);
        return "authors/edit";
    }

    @PostMapping("/authors/edit")
    public String edit(Model model, @Valid Author item, BindingResult bindingResult) {
        // Проверяем ошибки.
        if (bindingResult.hasErrors()) {
            StringBuilder error = new StringBuilder();
            // устанавливаем сообщения об ошибках
            for (FieldError fe : bindingResult.getFieldErrors()) {
                error.append(fe.getDefaultMessage()).append("<br/>");
            }

            model.addAttribute("error", error.toString());
            model.addAttribute("item", item);

            return "authors/edit";
        }

        try {
            //Из модели вместо null может прийти пустая строка и тогда MONGO не генерит ID,
            //в этом случае принудительно устанавливаем ID в null
            if (item.getId() != null && item.getId().isEmpty())
                item.setId(null);

            service.save(item);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("item", item);
            return "authors/edit";
        }

        return "redirect:/authors/";
    }

    @GetMapping("/authors/delete")
    public String delete(Model model, @RequestParam(value = "id") String id) {
        try {
            service.deleteById(id);
            return "redirect:/authors/";
        } catch (AuthorHasBooksException e) {
            e.printStackTrace();
            String error = "Невозможно удалить автора, т.к. у него есть книги.";
            model.addAttribute("error", error);

            try {
                model.addAttribute("item", service.findById(id));
            } catch (AuthorNotFoundException ex) {
                error += "<br/>" + String.format("Автор с кодом %s не найден.", id);
                model.addAttribute("error", error);
                ex.printStackTrace();
            }

            return "authors/edit";
        }
    }
}
