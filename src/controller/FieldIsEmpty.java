package controller;

public class FieldIsEmpty extends Exception {
    // Attributes
    
    // Constructeurs
    public FieldIsEmpty(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Le champs suivant est vide : " + super.getMessage();
    }
}
