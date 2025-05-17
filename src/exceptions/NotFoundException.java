package exceptions;

public class NotFoundException extends DAOOperationFailedException {
    public NotFoundException(String objectType, Long objectId, String message) {
        super(objectType, objectId, message);
    }

    public NotFoundException(String objectType, Integer objectId, String message) {
        super(objectType, objectId, message);
    }

    public NotFoundException(String objectType, Character objectId, String message) {
        super(objectType, objectId, message);
    }

    @Override
    public String getOperationType() {
        return "consultation";
    }
}
