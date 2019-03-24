package com.github.steveice10.opennbt.conversion;

/**
 * An exception thrown when an error occurs while unregistering a converter.
 */
public class ConverterUnregisterException extends RuntimeException {
    private static final long serialVersionUID = -2022049594558041160L;

    public ConverterUnregisterException() {
        super();
    }

    public ConverterUnregisterException(String message) {
        super(message);
    }

    public ConverterUnregisterException(Throwable cause) {
        super(cause);
    }

    public ConverterUnregisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
