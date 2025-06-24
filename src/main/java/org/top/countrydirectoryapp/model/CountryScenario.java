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
        validationCode(code);
        Optional<Country> country = storage.get(code);
        if (country.isEmpty()) {
            throw new CountryNotFoundException(code);
        }
        return country.get();
    }

    // сохранение новой страны
    public void store(Country country) {
        String code = country.getIsoAlpha2();
        validationCode(code);
        if(storage.get(code).isPresent()) {
            throw new DuplicatedCodeException(code);
        }
        storage.store(country);
    }

    // редактирование страны по коду
    public void edit(Country country) {
        String code = country.getIsoAlpha2();
        validationCode(code);
        if(storage.get(code).isEmpty()) {
            throw new CountryNotFoundException(code);
        }
        storage.edit(country);
    }

    // удаление страны по коду
    public void delete(String isoAloha2) {
        validationCode(isoAloha2);
        if(storage.get(isoAloha2).isEmpty()) {
            throw new CountryNotFoundException(isoAloha2);
        }
        storage.delete(isoAloha2);
    }

    private void validationCode(String code) {
        if (code == null) {
            throw new InvalidCodeException("null", "code is null");
        }
        if (!code.matches("[A-Z]{2}")) {
            throw new InvalidCodeException(code, "code can contains only two uppercase letters");
        }
    }
}
