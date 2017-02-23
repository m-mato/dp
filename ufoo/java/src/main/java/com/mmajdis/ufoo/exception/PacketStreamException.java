package com.mmajdis.ufoo.exception;

/**
 * @author Matej Majdis
 */
public class PacketStreamException extends RuntimeException {

    public PacketStreamException() {
    }

    public PacketStreamException(String message) {
        super(message);
    }

    public PacketStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketStreamException(Throwable cause) {
        super(cause);
    }
}
