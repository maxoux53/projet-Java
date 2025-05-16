package model;

import exceptions.ProhibitedValueException;

public class EmployeePlace {
    // Attributes
    private String employeeFirstName, employeeLastName;
    private City city;
    
    // Constructors
    public EmployeePlace(String employeeFirstName, String employeeLastName, String cityName, Integer cityZipCode, String countryName) throws ProhibitedValueException {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        city = new City(cityZipCode, cityName, countryName);
    }
    
    // Getters
    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public City getCity() {
        return city;
    }
}
