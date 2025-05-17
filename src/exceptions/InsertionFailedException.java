package exceptions;

public class InsertionFailedException extends DAOOperationFailedException {
    public InsertionFailedException(String objectType, Long objectId, String message) {
        super(objectType, objectId, message);
    }

    public InsertionFailedException(String objectType, Integer objectId, String message) {
        super(objectType, objectId, message);
    }

    public InsertionFailedException(String objectType, Character objectId, String message) {
        super(objectType, objectId, message);
    }

    @Override
    public String getOperationType() {
        return "insertion";
    }
}
