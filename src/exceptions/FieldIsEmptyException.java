package exceptions;

public class FieldIsEmptyException extends Exception {
    public FieldIsEmptyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Le champ suivant est vide : " + super.getMessage();
    }
}
