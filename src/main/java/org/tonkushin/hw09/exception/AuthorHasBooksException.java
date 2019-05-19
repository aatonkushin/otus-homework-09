package org.tonkushin.hw09.exception;

public class AuthorHasBooksException extends Exception {
    public AuthorHasBooksException() {
        super("Author has books");
    }
}
