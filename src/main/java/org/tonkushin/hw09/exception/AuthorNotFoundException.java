package org.tonkushin.hw09.exception;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException() {
        super("Author not found");
    }
}
