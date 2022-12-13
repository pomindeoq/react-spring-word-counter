package com.counter.words.exception;

import com.counter.words.exception.constants.ErrorCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class FileUploadExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", ErrorCodes.FILE_SIZE_TOO_LARGE.toString());
        body.put("message", ErrorCodes.FILE_SIZE_TOO_LARGE.getException().getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                "*");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(responseHeaders).body(body);
    }
}
