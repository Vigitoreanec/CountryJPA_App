package org.top.countrydirectoryapp.storage;

import jakarta.persistence.*;
import org.top.countrydirectoryapp.model.Country;

@Entity
@Table(name = "country")
public class CountryDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "isoAlpha2", length = 2, nullable = false, unique = true)
    private String isoAlpha2;

    @Column(name = "shortName", length = 50, nullable = false)
    private String shortName;

    @Column(name = "fullName", length = 100, nullable = false)
    private String fullName;

    @Column(name = "isoAlpha3", length = 3, nullable = false)
    private String isoAlpha3;

    @Column(name = "isoNumeric", length = 3, nullable = false)
    private String isoNumeric;

    @Column(name = "population", nullable = false)
    private Long population;

    @Column(name = "square", nullable = false)
    private Long square;

    public CountryDbEntity(Country country) {
        id = null;
        shortName = country.getShortName();
        fullName = country.getFullName();
        isoAlpha2 = country.getIsoAlpha2();
        isoAlpha3 = country.getIsoAlpha3();
        isoNumeric = country.getIsoNumeric();
        population = country.getPopulation();
        square = country.getSquare();
    }

    public CountryDbEntity() {

    }

    public Country asCountry() {
        return new Country(shortName, fullName,
                isoAlpha2, isoAlpha3, isoNumeric,
                population, square);
    }

    public Integer getId() {
        return id;
    }

    public String getIsoAlpha2() {
        return isoAlpha2;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public Long getPopulation() {
        return population;
    }

    public Long getSquare() {
        return square;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsoAlpha2(String isoAlpha2) {
        this.isoAlpha2 = isoAlpha2;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public void setSquare(Long square) {
        this.square = square;
    }
}
