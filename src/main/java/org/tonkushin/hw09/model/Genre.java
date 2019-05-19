package org.tonkushin.hw09.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Genre {
    @Id
    private String id;

    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }
}
