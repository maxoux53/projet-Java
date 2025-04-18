package controller;

public class WrongType extends RuntimeException {
    public WrongType(String message) {
        super(message);
    }

  @Override
  public String getMessage() {
    return "Le nombre suivant est incorrect : " + super.getMessage();
  }
}
