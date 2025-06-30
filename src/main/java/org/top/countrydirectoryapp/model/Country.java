package org.top.countrydirectoryapp.model;

public class Country {

    private String shortName;   // короткое наименование страны (Россия)
    private String fullName;    // полное наименование страны (Российская Федерация)
    private String isoAlpha2;   // двухбуквенный код страны (RU)
    private String isoAlpha3;   // трехбуквенный код страны (RUS)
    private String isoNumeric;  // числовой код страны - строка (643)
    private Long population;    // население страны - кол-во человек (146 150 789)
    private Long square;        // страны кв. км. (17 125 191)



    public Country(String shortName, String fullName,
                   String isoAlpha2, String isoAlpha3,
                   String isoNumeric, Long population, Long square) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.isoAlpha2 = isoAlpha2;
        this.isoAlpha3 = isoAlpha3;
        this.isoNumeric = isoNumeric;
        this.population = population;
        this.square = square;
    }

    public Country() {
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsoAlpha2() {
        return isoAlpha2;
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIsoAlpha2(String isoAlpha2) {
        this.isoAlpha2 = isoAlpha2;
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

    public String isValid() {
        if (this.isoAlpha2 != null && this.isoAlpha2.matches("^[A-Z]{2}$")) {
            return getIsoAlpha2();
        }
        if (this.isoAlpha3 != null && this.isoAlpha3.matches("^[A-Z]{3}$")) {
            return getIsoAlpha3();
        }
        if (this.isoNumeric != null && this.isoNumeric.matches("^\\d+$")) {
            return getIsoNumeric();
        }
        return null;
    }
}
