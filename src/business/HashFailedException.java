package business;

public class HashFailedException extends Exception {
    private String additionalContext;

    public HashFailedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error interacting with hash algorithm: " + super.getMessage();
    }
}
