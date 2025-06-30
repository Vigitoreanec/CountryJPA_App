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
                "643", 146150789L, 17125191L));
        countries.add(new Country("USA", "United States of America", "US", "USA",
                "840", 331002651L, (long) 9833517));
        countries.add(new Country("Germany", "Federal Republic of Germany", "DE", "DEU",
                "276", 83190556L, 357022L));
    }

    @Override
    public List<Country> getAll() {
        return countries;
    }

    @Override
    public Optional<Country> get(String code) {
        if(isAlpha2(code)) {
            return countries.stream().filter(country -> country.getIsoAlpha2().equals(code)).findFirst();
        }
        if(isAlpha3(code)) {
            return countries.stream().filter(country -> country.getIsoAlpha3().equals(code)).findFirst();
        }
        if(isNumeric(code)) {
            return countries.stream().filter(country -> country.getIsoNumeric().equals(code)).findFirst();
        }
        return Optional.empty();
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

    @Override
    public void store(Country country) {
        countries.add(country);
    }

    @Override
    public void edit(Country country) {
        delete(country.isValid());
        store(country);
    }

    @Override
    public void delete(String code) {
        countries.removeIf(c -> c.isValid().equals(code));
    }
}
