package model;

import exceptions.ProhibitedValueException;

import java.time.LocalDate;

public class Customer {
    private Integer loyaltyCardNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private Integer phone;
    private Long vatNumber;
    private Integer loyaltyPoints;

    public Customer(Integer loyaltyCardNumber) {
        this.loyaltyCardNumber = loyaltyCardNumber;
    }

    public void setFirstName(String firstName) throws ProhibitedValueException {
        if (firstName.length() > 20) {
            throw new ProhibitedValueException("Le prénom ne peut pas dépasser 20 caractères");
        }

        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws ProhibitedValueException {
        if (lastName.length() > 25) {
            throw new ProhibitedValueException("Le nom ne peut pas dépasser 25 caractères");
        }

        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) throws ProhibitedValueException {
        if (email.length() > 50) {
            throw new ProhibitedValueException("L'email ne peut pas dépasser 50 caractères");
        }

        this.email = email;
    }

    public void setPhone(Integer phone) throws ProhibitedValueException {
        if (phone <= 0) {
            throw new ProhibitedValueException("Le numéro de téléphone ne peut ni être nul ni commencer par un moins");
        }

        this.phone = phone;
    }

    public void setVatNumber(Long vatNumber) throws ProhibitedValueException {
        if (vatNumber <= 0) {
            throw new ProhibitedValueException("Le numéro de TVA ne peut ni être nulle ni commencer par un moins");
        }

        this.vatNumber = vatNumber;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) throws ProhibitedValueException {
        if (loyaltyPoints < 0) {
            throw new ProhibitedValueException("Le nombre de points de fidélité ne peut pas être négatif");
        }

        this.loyaltyPoints = loyaltyPoints;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() throws ProhibitedValueException {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new ProhibitedValueException("La date de naissance ne peut pas être dans le futur");
        }

        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhone() {
        return phone;
    }

    public Long getVatNumber() {
        return vatNumber;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public Integer getLoyaltyCardNumber() {
        return loyaltyCardNumber;
    }
}
