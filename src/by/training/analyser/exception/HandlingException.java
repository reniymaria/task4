package by.training.analyser.exception;

public class HandlingException extends Exception {

    public HandlingException(String message) {
        super(message);
    }

    public HandlingException(String message, Exception cause) {
        super(message, cause);
    }
}
