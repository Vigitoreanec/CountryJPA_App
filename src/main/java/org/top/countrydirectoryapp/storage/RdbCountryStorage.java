package org.top.countrydirectoryapp.storage;

import org.springframework.stereotype.Repository;
import org.top.countrydirectoryapp.model.Country;
import org.top.countrydirectoryapp.model.CountryStorage;

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
    public Optional<Country> get(String isoAlpha2) {
        Optional<CountryDbEntity> dbCountry = repository.findByIsoAlpha2(isoAlpha2);
        return dbCountry.map(CountryDbEntity::asCountry);
    }

    @Override
    public void store(Country country) {
        CountryDbEntity dbCountry = new CountryDbEntity(country);
        repository.save(dbCountry);
    }

    @Override
    public void edit(Country country) {
        Optional<CountryDbEntity> dbCountry = repository.findByIsoAlpha2(country.getIsoAlpha2());
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
        Optional<CountryDbEntity> dbCountry = repository.findByIsoAlpha2(code);
        dbCountry.ifPresent(repository::delete);
    }

}
