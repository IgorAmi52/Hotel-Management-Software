package com.exceptions;

public class BadLoginException extends Exception {
    public BadLoginException(String message) {
        super(message);
    }
}
