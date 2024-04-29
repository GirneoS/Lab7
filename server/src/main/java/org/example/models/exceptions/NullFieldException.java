package org.example.models.exceptions;

/**
 * This exception is thrown when the user enters null value where it can't be
 */
public class NullFieldException extends RuntimeException{

    public NullFieldException(String message) {
        super(message);
    }
}
