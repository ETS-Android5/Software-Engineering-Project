package comp3350.breadtunes.exception;

public class RecordDoesNotExistException extends PersistenceException {
    public RecordDoesNotExistException(String message) {
        super(message);
    }
}
