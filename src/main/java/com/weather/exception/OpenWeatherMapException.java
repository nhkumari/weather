package com.weather.exception;


public class OpenWeatherMapException extends RuntimeException {
    public OpenWeatherMapException(Exception exception) {
        super(String.format("Open weather map threw exception : '%s' ", exception.getMessage()));
    }
}
