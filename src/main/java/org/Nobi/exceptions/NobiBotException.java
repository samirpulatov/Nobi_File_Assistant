package org.Nobi.exceptions;

public class NobiBotException extends RuntimeException {
    public NobiBotException(String message) {
        super(message);
    }

    public NobiBotException(String message, Throwable cause) {
        super(message, cause);
    }
}
