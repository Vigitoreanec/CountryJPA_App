package org.top.countrydirectoryapp.stub;

import org.top.countrydirectoryapp.model.Country;
import org.top.countrydirectoryapp.model.CountryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryStorageStub implements CountryStorage {
    private final List<Country> countries = new ArrayList<Country>();
    public CountryStorageStub() {
        countries.add(new Country("Russia", "Russian Federation", "RU", "RUS",
                "643", 146150789L,  17125191L));
        countries.add(new Country("USA", "United States of America", "US", "USA",
                "840", 331002651L, (long) 9833517));
        countries.add(new Country("Germany", "Federal Republic of Germany", "DE", "DEU",
                "276", 83190556L,  357022L));
    }

    @Override
    public List<Country> getAll() {
        return countries;
    }

    @Override
    public Optional<Country> get(String isoAloha2) {
        return Optional.empty();
    }

    @Override
    public void store(Country country) {

    }

    @Override
    public void edit(Country country) {

    }

    @Override
    public void delete(String code) {

    }
}
