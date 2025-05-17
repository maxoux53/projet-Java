package model;

import exceptions.ProhibitedValueException;

public class Brand {
    private Integer id;
    private String name;

    public Brand(Integer id, String name) throws ProhibitedValueException {
        this.id = id;
        setName(name);
    }

    // Setters
    public void setName(String name) throws ProhibitedValueException {
        if (name.length() > 50) {
            throw new ProhibitedValueException("La longueur du nom de la marque ne peut pas dépasser 50 caractères");
        }

        this.name = name;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
