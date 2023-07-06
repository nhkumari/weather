package com.weather.service;

import com.weather.dto.Request;
import com.weather.dto.OpenApiResponse;
import com.weather.dto.WeatherResponse;
import com.weather.exception.EmptyGeoLocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherMapService openWeatherService;

    public OpenApiResponse fetchWeatherForecast(Request request) {
        if (null == request.getCity() && (null == request.getLatitude() || null == request.getLongitude())) {
            throw new EmptyGeoLocationException(request);
        }
        return openWeatherService.fetchWeatherForecast(request);
    }

    public WeatherResponse fetchCurrentWeather(Double latitude, Double longitude) {
        return openWeatherService.fetchCurrentWeather(latitude, longitude);
    }
}
