package org.tonkushin.hw09.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Book {
    @Id
    private String id;

    private String name;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;

    private List<String> comments;

    public Book() {
    }

    public Book(String name, Genre genre, Author author) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
}
