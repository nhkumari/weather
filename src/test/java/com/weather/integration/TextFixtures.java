package com.weather.integration;

import com.weather.dto.Request;

public class TextFixtures {
    public static final String API_FORECAST = "/api/forecast";
    public static final String API_WEATHER = "/api/weather/{latitude}/{longitude}";
    public static final Request WEATHER_REQUEST_WITH_CITY = Request.builder()
            .city("Essen")
            .build();
    public static final Request WEATHER_REQUEST_WITH_LOCATION = Request.builder()
            .latitude(36.7783)
            .longitude(119.4179)
            .build();
    public static final Request INVALID_WEATHER_REQUEST = Request.builder()
            .latitude(361.7783)
            .longitude(19.4179)
            .build();
    public static final Request EMPTY_WEATHER_REQUEST = Request.builder()
            .build();

    public static final String RESPONSE = "Open weather map threw exception : '400 Bad Request: \"{\"cod\":\"400\",\"message\":\"wrong latitude\"}\"' ";
}
