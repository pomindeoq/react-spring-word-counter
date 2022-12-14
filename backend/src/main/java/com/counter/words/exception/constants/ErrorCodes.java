package com.counter.words.exception.constants;

import com.counter.words.exception.GeneralException;

public enum ErrorCodes {

    FILE_MISSING("Please select a file!"),
    FILE_EMPTY("Please select not empty files!"),
    FILE_SIZE_TOO_LARGE("File size is too large. Please upload files below 128KB!"),
    FILE_TYPE_NOT_VALID("files type is not supported! Only .txt files are allowed"),

    FILE_DATA_INVALID("This file/files data can not be read and parsed");
    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }

    public void throwException() {
        throw new GeneralException(this.message, this.name());
    }

    public GeneralException getException() {
        return new GeneralException(this.message, this.name());
    }
}
