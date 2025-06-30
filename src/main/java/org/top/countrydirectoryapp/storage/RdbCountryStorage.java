package org.top.countrydirectoryapp.storage;

import org.springframework.stereotype.Repository;
import org.top.countrydirectoryapp.model.Country;
import org.top.countrydirectoryapp.model.CountryNotFoundException;
import org.top.countrydirectoryapp.model.CountryStorage;
import org.top.countrydirectoryapp.model.InvalidCodeException;

import java.util.List;
import java.util.Optional;

@Repository
public class RdbCountryStorage implements CountryStorage {

    private final CountryRepository repository;

    public RdbCountryStorage(CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Country> getAll() {
        return repository.findAll().stream()
                .map(CountryDbEntity::asCountry)
                .toList();
    }

    @Override
    public Optional<Country> get(String code) {
        String value = checkedCodeType(code);
        try {
            return switch (value) {
                case "alpha2" -> repository.findByIsoAlpha2(code)
                        .map(CountryDbEntity::asCountry);
                case "alpha3" -> repository.findByIsoAlpha3(code)
                        .map(CountryDbEntity::asCountry);
                case "numeric" -> repository.findByIsoNumeric(code)
                        .map(CountryDbEntity::asCountry);
                default -> Optional.empty();
            };
        } catch (InvalidCodeException e) {
            throw new InvalidCodeException(code, e.getMessage());
        }
    }

    @Override
    public void store(Country country) {
        CountryDbEntity dbCountry = new CountryDbEntity(country);
        repository.save(dbCountry);
    }

    @Override
    public void edit(Country country) {
        Optional<CountryDbEntity> dbCountry = repository.findByIsoNumeric(country.getIsoNumeric());
        if (dbCountry.isEmpty()) {
            return;
        }
        CountryDbEntity dbCountryEntity = dbCountry.get();

        dbCountryEntity.setShortName(country.getShortName());
        dbCountryEntity.setFullName(country.getFullName());
        dbCountryEntity.setIsoAlpha2(country.getIsoAlpha2());
        dbCountryEntity.setIsoAlpha3(country.getIsoAlpha3());
        dbCountryEntity.setIsoNumeric(country.getIsoNumeric());
        dbCountryEntity.setPopulation(country.getPopulation());
        dbCountryEntity.setSquare(country.getSquare());
        repository.save(dbCountryEntity);
    }

    @Override
    public void delete(String code) {
        String value = checkedCodeType(code);
        Optional<CountryDbEntity> dbCountry = Optional.empty();
        switch (value) {
            case "alpha2" -> dbCountry = repository.findByIsoAlpha2(code);
            case "alpha3" -> dbCountry = repository.findByIsoAlpha3(code);
            case "numeric" -> dbCountry = repository.findByIsoNumeric(code);
            default -> throw new CountryNotFoundException(code);
        }

        dbCountry.ifPresent(repository::delete);
    }


    private String checkedCodeType(String code) {

        if (code.length() == 2 && code.matches("[A-Z]{2}")) {
            return "alpha2";
        }

        if (code.length() == 3 && code.matches("[A-Z]{3}")) {
            return "alpha3";
        }

        if (code.matches("\\d+")) {
            return "numeric";
        }

        throw new CountryNotFoundException(code);
    }
}
