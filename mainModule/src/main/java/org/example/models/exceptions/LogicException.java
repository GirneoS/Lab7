package org.example.models.exceptions;

/**
 * This exception is thrown when the user enters an illogical value
 */
public class LogicException extends RuntimeException{

    public LogicException(String message) {
        super(message);
    }
}
