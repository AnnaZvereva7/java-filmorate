package ru.yandex.practicum.filmorate.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum.filmorate")
public class ErrorHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> nullObject(NullPointerException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> validationError(ValidationException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(KeyAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> wrongIdError(KeyAlreadyExistsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> otherError(Exception e) {
        return Map.of("error", e.getMessage());
    }


}
