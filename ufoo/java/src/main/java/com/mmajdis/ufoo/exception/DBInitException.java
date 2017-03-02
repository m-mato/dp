package com.mmajdis.ufoo.exception;

/**
 * @author Matej Majdis
 */
public class DBInitException extends RuntimeException {
    public DBInitException() {
    }

    public DBInitException(String message) {
        super(message);
    }

    public DBInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBInitException(Throwable cause) {
        super(cause);
    }
}
