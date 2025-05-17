package exceptions;

public class DeleteFailedException extends DAOOperationFailedException {
    public DeleteFailedException(String objectType, Long objectId, String message) {
        super(objectType, objectId, message);
    }

    public DeleteFailedException(String objectType, Integer objectId, String message) {
        super(objectType, objectId, message);
    }

    public DeleteFailedException(String objectType, Character objectId, String message) {
        super(objectType, objectId, message);
    }

    @Override
    public String getOperationType() {
        return "suppression";
    }
}
