package org.top.countrydirectoryapp.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryScenario {

    private final CountryStorage storage;

    public CountryScenario(CountryStorage storage) {
        this.storage = storage;
    }

    // получение списка всех стран
    public List<Country> listAll() {
        return storage.getAll();
    }

    // получение списка страны по коду
    public Country get(String code) {
        Optional<Country> country = Optional.empty();
        if (isAlpha2(code)) {
            country = storage.get(code);
        } else if (isAlpha3(code)) {
            country = storage.get(code);
        } else if (isNumeric(code)) {
            country = storage.get(code);
        }
        if (country.isEmpty()) {
            throw new CountryNotFoundException(code);
        }
        return country.get();
    }

    // сохранение новой страны
    public void store(Country country) {
        String code = country.getIsoAlpha2();
        if (storage.get(code).isPresent()) {
            throw new DuplicatedCodeException(code);
        }
        storage.store(country);
    }

    // редактирование страны по коду
    public void edit(Country country) {
        if (!isAlpha2(country.getIsoAlpha2()) && !isAlpha3(country.getIsoAlpha3())
                && !isNumeric(country.getFullName())) {
            throw new InvalidCodeException("Code Country");
        }
        storage.edit(country);
    }

    // удаление страны по коду
    public void delete(String code) {

        if (storage.get(code).isEmpty()) {
            throw new CountryNotFoundException(code);
        }
        if (!isAlpha2(code) && !isAlpha3(code) && !isNumeric(code)) {
            throw new InvalidCodeException(code);
        }
        storage.delete(code);
    }

    public static boolean isAlpha2(String code) {
        return code.length() == 2 && code.matches("[A-Z]{2}");
    }

    public static boolean isAlpha3(String code) {
        return code.length() == 3 && code.matches("[A-Z]{3}");
    }

    public static boolean isNumeric(String code) {
        return code.matches("\\d+");
    }
}
