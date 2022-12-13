package com.counter.words.exception;

public class GeneralException extends RuntimeException {
    final String code;

    public GeneralException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
