package org.top.countrydirectoryapp.model;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String code) {
        super("Country with code " + code + " not found");
    }
}
