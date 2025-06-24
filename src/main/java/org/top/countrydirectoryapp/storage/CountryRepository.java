package org.top.countrydirectoryapp.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryDbEntity,Integer> {

    Optional<CountryDbEntity> findByIsoAlpha2(String isoAlpha2);
}
