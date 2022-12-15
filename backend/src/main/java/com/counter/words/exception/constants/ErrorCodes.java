package com.counter.words.exception.constants;

import com.counter.words.exception.GeneralException;

public enum ErrorCodes {

    FILES_MISSING("Please select a file!"),
    FILES_EMPTY("Please select not empty files!"),
    FILES_SIZE_TOO_LARGE("File size is too large. Please upload files below 128KB!"),
    FILES_TYPE_NOT_VALID("Files type is not supported! Only .txt files are allowed"),

    FILES_DATA_INVALID("Files data is not valid, please upload another file");
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
