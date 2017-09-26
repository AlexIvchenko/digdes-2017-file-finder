package com.github.alexivchenko.filefinder.core;

/**
 * @author Alex Ivchenko
 */
public class ParseException extends RuntimeException {
    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
