package ru.craftside.lunchvote.util.exception;

import org.springframework.http.HttpStatus;

/**
 * Created at 06.01.2020
 *
 * @author Pavel Tolstenkov
 */
public enum ErrorType {
    DATA_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY);

    private final HttpStatus status;

    ErrorType(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
