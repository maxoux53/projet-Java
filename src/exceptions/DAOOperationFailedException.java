package exceptions;

public abstract class DAOOperationFailedException extends Exception {
    private String objectType;
    private Object objectId;

    private DAOOperationFailedException(String objectType, String message) {
        super(message);
        this.objectType = objectType;
    }

    public DAOOperationFailedException(String objectType, Long objectId, String message) {
        this(objectType, message);
        this.objectId = objectId;
    }

    public DAOOperationFailedException(String objectType, Integer objectId, String message) {
        this(objectType, message);
        this.objectId = objectId;
    }

    public DAOOperationFailedException(String objectType, Character objectId, String message) {
        this(objectType, message);
        this.objectId = objectId;
    }

    public abstract String getOperationType();

    @Override
    public String getMessage() {
        StringBuilder output = new StringBuilder();

        if (getOperationType() == null) {
            output.append("L'opération a échoué !");
        } else {
            output.append("L'opération de ").append(getOperationType()).append(" sur l'objet ").append(objectType);

            if (objectId != null) {
                output.append(" dont l'identifiant est '").append(objectId).append("'");
            }

            output.append(" a échoué !");
        }

        output.append("\nVoir: ").append(super.getMessage());

        return output.toString();
    }
}
