package org.top.countrydirectoryapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.top.countrydirectoryapp.api.message.CommonApiMessage.ErrorMessage;
import org.top.countrydirectoryapp.model.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {
    private final CountryScenario countries;

    public CountryController(CountryScenario countries) {
        this.countries = countries;
    }

    @GetMapping
    public List<Country> getAll() {
        return countries.listAll();
    }

    @GetMapping("{code}")
    public Country get(@PathVariable String code) {
        return countries.get(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void store(@RequestBody Country country) {
        countries.store(country);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void edit(@RequestBody Country country) {
        countries.edit(country);
    }

    @DeleteMapping("{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        countries.delete(code);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleAirportNotFound(CountryNotFoundException e) {
        return new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(DuplicatedCodeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleDuplicatedCode(DuplicatedCodeException e) {
        return new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(InvalidCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidCode(InvalidCodeException e) {
        return new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
    }
}
