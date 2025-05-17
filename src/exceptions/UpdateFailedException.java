package exceptions;

public class UpdateFailedException extends DAOOperationFailedException {
    public UpdateFailedException(String objectType, Long objectId, String message) {
        super(objectType, objectId, message);
    }

    public UpdateFailedException(String objectType, Integer objectId, String message) {
        super(objectType, objectId, message);
    }

    public UpdateFailedException(String objectType, Character objectId, String message) {
        super(objectType, objectId, message);
    }

    @Override
    public String getOperationType() {
        return "mise-à-jour";
    }
}
