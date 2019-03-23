package com.github.steveice10.opennbt.tag;

/**
 * An exception thrown when an error occurs while unregistering a tag.
 */
public class TagUnregisterException extends RuntimeException {
    private static final long serialVersionUID = -2022049594558041160L;

    public TagUnregisterException() {
        super();
    }

    public TagUnregisterException(String message) {
        super(message);
    }

    public TagUnregisterException(Throwable cause) {
        super(cause);
    }

    public TagUnregisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
