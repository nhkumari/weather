package com.weather.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EmptyGeoLocationException.class)
    public ResponseEntity<String> emptyGeoLocationException(EmptyGeoLocationException e) {
        String errorMessage = format(e.getMessage());
        log.error(errorMessage);
        return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(OpenWeatherMapException.class)
    public ResponseEntity<String> openWeatherMapException(OpenWeatherMapException e) {
        String errorMessage = format(e.getMessage());
        log.error(errorMessage);
        return ResponseEntity.status(BAD_REQUEST).body(errorMessage);
    }
}
