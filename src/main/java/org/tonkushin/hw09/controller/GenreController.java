package org.tonkushin.hw09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tonkushin.hw09.exception.GenreHasBooksException;
import org.tonkushin.hw09.exception.GenreNotFoundException;
import org.tonkushin.hw09.model.Genre;
import org.tonkushin.hw09.service.GenreService;

import javax.validation.Valid;

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
                model.addAttribute("error", String.format("Жанр с кодом %s не найден", id));
            }
        }
        model.addAttribute("item", item);
        return "genres/edit";
    }

    @PostMapping("/genres/edit")
    public String edit(Model model, @Valid Genre item, BindingResult bindingResult) {
        // Проверяем ошибки.
        if (bindingResult.hasErrors()) {
            StringBuilder error = new StringBuilder();
            // устанавливаем сообщения об ошибках
            for (FieldError fe : bindingResult.getFieldErrors()) {
                error.append(fe.getDefaultMessage()).append("<br/>");
            }

            model.addAttribute("error", error.toString());
            model.addAttribute("item", item);

            return "genres/edit";
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
            String error = "Невозможно удалить жанр, т.к. у него есть книги.";
            model.addAttribute("error", error);

            try {
                model.addAttribute("item", service.findById(id));
            } catch (GenreNotFoundException ex) {
                error += "<br/>" + String.format("Жанр с кодом %s не найден.", id);
                model.addAttribute("error", error);
                ex.printStackTrace();
            }

            return "genres/edit";
        }
    }
}
