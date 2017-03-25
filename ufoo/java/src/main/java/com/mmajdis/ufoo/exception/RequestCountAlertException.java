package com.mmajdis.ufoo.exception;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
public class RequestCountAlertException extends RuntimeException {

    public RequestCountAlertException() {
    }

    public RequestCountAlertException(String message) {
        super(message);
    }

    public RequestCountAlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestCountAlertException(Throwable cause) {
        super(cause);
    }
}

