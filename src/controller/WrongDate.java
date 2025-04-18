package controller;

public class WrongDate extends RuntimeException {
    public WrongDate(String message) {
        super(message);
    }

  @Override
  public String getMessage() {
    return "La date entrée est invalide !";
  }
}
