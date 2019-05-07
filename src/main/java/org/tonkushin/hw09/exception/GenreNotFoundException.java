package org.tonkushin.hw09.exception;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException() {
        super("Genre not found");
    }
}
