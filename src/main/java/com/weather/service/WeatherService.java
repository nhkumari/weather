package com.weather.service;

import com.weather.dto.OpenApiResponse;
import com.weather.dto.Request;
import com.weather.dto.WeatherResponse;

public interface WeatherService {
    OpenApiResponse fetchWeatherForecast(Request request);

    WeatherResponse fetchCurrentWeather(Double latitude, Double longitude);
}
