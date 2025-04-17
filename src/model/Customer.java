package model;

import java.time.LocalDate;

public class Customer {
    private Integer loyaltyCardNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private Integer phone;
    private Integer vatNumber;
    private Integer loyaltyPoints;

    public Customer(Integer loyaltyCardNumber) {
        this.loyaltyCardNumber = loyaltyCardNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setVatNumber(Integer vatNumber) {
        this.vatNumber = vatNumber;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhone() {
        return phone;
    }

    public Integer getVatNumber() {
        return vatNumber;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public Integer getLoyaltyCardNumber() {
        return loyaltyCardNumber;
    }
}
