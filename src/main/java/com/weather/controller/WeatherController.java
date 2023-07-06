package com.weather.controller;

import com.weather.dto.Request;
import com.weather.dto.OpenApiResponse;
import com.weather.dto.WeatherResponse;
import com.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @PostMapping(value = "/forecast")
    public OpenApiResponse fetchWeatherForecast(@RequestBody Request request) {
        log.info("Fetching weather report for given input {}", request);
        return weatherService.fetchWeatherForecast(request);
    }

    @GetMapping(value = "/weather/{latitude}/{longitude}")
    WeatherResponse fetchCurrentWeather(@PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude) {
        log.info("Fetching weather report for given latitude {} and longitude {}", latitude, longitude);
        return weatherService.fetchCurrentWeather(latitude, longitude);
    }
}
