package model;

public class City {
    private Integer zipCode;
    private String name;
    private String country;

    public City(Integer zipCode, String name) {
        this.zipCode = zipCode;
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
