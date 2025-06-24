package org.top.countrydirectoryapp.model;

public class DuplicatedCodeException extends RuntimeException {
    public DuplicatedCodeException(String code) {
        super("Country with code " + code + " already exists");
    }
}
