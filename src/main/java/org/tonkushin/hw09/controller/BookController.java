package org.tonkushin.hw09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tonkushin.hw09.exception.BookNotFoundException;
import org.tonkushin.hw09.model.Book;
import org.tonkushin.hw09.service.AuthorService;
import org.tonkushin.hw09.service.BookService;
import org.tonkushin.hw09.service.GenreService;

import javax.validation.Valid;

@Controller
public class BookController {
    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService service, AuthorService authorService, GenreService genreService) {
        this.service = service;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/books/")
    public String list(Model model) {
        model.addAttribute("items", service.findAll());
        return "books/list";
    }

    @GetMapping("/books/edit")
    public String edit(Model model, @RequestParam(value = "id", required = false) String id) {
        Book item = new Book();

        //Создание новой записи
        if (id != null) {
            //Редактирование существующей записи
            try {
                item = service.findById(id);
            } catch (BookNotFoundException e) {
                e.printStackTrace();
                model.addAttribute("error", String.format("Книга с кодом %s не найдена", id));
            }
        }
        model.addAttribute("item", item);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "books/edit";
    }

    @PostMapping("/books/edit")
    public String edit(Model model, @Valid Book item, BindingResult bindingResult) {
        // Проверяем ошибки.
        if (bindingResult.hasErrors()) {
            StringBuilder error = new StringBuilder();
            // устанавливаем сообщения об ошибках
            for (FieldError fe : bindingResult.getFieldErrors()) {
                error.append(fe.getDefaultMessage()).append("<br/>");
            }

            model.addAttribute("error", error.toString());
            model.addAttribute("item", item);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());

            return "books/edit";
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
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "books/edit";
        }

        return "redirect:/books/";
    }

    @GetMapping("/books/delete")
    public String delete(@RequestParam(value = "id") String id) {
        service.deleteById(id);
        return "redirect:/books/";

    }

}
