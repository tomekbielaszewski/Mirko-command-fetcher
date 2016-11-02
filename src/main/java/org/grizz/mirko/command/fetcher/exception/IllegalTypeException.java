package org.grizz.mirko.command.fetcher.exception;

public class IllegalTypeException extends RuntimeException {
    private final String type;

    public IllegalTypeException(String type) {
        this.type = type;
    }
}
