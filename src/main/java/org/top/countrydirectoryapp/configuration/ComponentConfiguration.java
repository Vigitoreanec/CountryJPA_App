package org.top.countrydirectoryapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.top.countrydirectoryapp.model.CountryScenario;
import org.top.countrydirectoryapp.storage.CountryRepository;
import org.top.countrydirectoryapp.stub.CountryStorageStub;

@Configuration
public class ComponentConfiguration {

    private final CountryRepository repository;

    public ComponentConfiguration(CountryRepository repository) {
        this.repository = repository;
    }

//    @Bean
//    public CountryScenario countries() {
//        return new CountryScenario(new RdbCountryStorage(repository));
//    }

    @Bean
    public CountryScenario countries() {
        return new CountryScenario(new CountryStorageStub());
    }
}
