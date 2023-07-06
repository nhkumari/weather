package com.weather.exception;

import com.weather.dto.Request;

public class EmptyGeoLocationException extends RuntimeException {
    public EmptyGeoLocationException(Request request) {
        super(String.format("Either of location co-ordinates or city is mandatory, provided latitude '%s', longitude '%s', city '%s'", request.getLatitude(), request.getLongitude(), request.getCity()));
    }
}
