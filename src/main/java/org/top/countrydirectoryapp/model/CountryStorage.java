package org.top.countrydirectoryapp.model;

import java.util.List;
import java.util.Optional;


public interface CountryStorage {

    // получение списка всех стран
    List<Country> getAll();

    // получение страны по коду
    Optional<Country> get(String isoAloha2);

    // сохранение новой страны
    void store(Country country);

    // редактирование страны по коду
    void edit(Country country);

    // удаление страны по коду
    void delete(String isoAloha2);
}

