package io.anjola.transactionreactive.exception.api;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiErrorResponse {

    private final String status;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;
    private final ZonedDateTime timestamp;

    public ApiErrorResponse(HttpStatus httpStatus, String path, String message) {
        this.status = "Fail";
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;

        timestamp = ZonedDateTime.now();
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getError(){
        return httpStatus.getReasonPhrase();
    }

    public String getStatus() {
        return status;
    }
}
