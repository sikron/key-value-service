package com.skronawi.keyvalueservice.api;

public class KeyNotExistingException extends RuntimeException {

    public KeyNotExistingException(String message) {
        super(message);
    }
}
