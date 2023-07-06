package com.weather.service;


import com.weather.dto.Request;
import com.weather.dto.OpenApiResponse;
import com.weather.dto.WeatherResponse;
import com.weather.exception.OpenWeatherMapException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    @Value("${OPEN_WEATHER_END_POINT}")
    private String weatherEndPoint;
    @Value("${APP_ID}")
    private String appId;

    private final RestTemplate restTemplate;

    public OpenWeatherMapServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public OpenApiResponse fetchWeatherForecast(Request request) {
        if (request.getCity() != null) {
            return fetchWeatherForecast(request.getCity());
        }
        return fetchWeatherForecast(request.getLatitude(), request.getLongitude());
    }

    public WeatherResponse fetchCurrentWeather(Double latitude, Double longitude) {
        ResponseEntity<WeatherResponse> response;
        WeatherResponse currentWeather = null;
        Map<String, String> uriVariables = formGeoLocationMap(latitude, longitude);
        String endPoint = weatherEndPoint.concat("weather?lat={latitude}&lon={longitude}").concat("&appid=").concat(appId);
        try {
            response = restTemplate.exchange(endPoint, HttpMethod.GET, null, WeatherResponse.class, uriVariables);
            currentWeather = response.getBody();
        } catch (HttpClientErrorException exception) {
            handleException(exception);
        }
        return currentWeather;
    }

    private OpenApiResponse fetchWeatherForecast(String city) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", city);
        String endPoint = weatherEndPoint.concat("forecast?q={city}").concat("&mode=json&appid=").concat(appId);
        return callOpenAPI(endPoint, uriVariables);
    }

    private OpenApiResponse fetchWeatherForecast(Double latitude, Double longitude) {
        Map<String, String> uriVariables = formGeoLocationMap(latitude, longitude);
        String endPoint = weatherEndPoint.concat("forecast?lat={latitude}&lon={longitude}").concat("&appid=").concat(appId);
        return callOpenAPI(endPoint, uriVariables);
    }

    private OpenApiResponse callOpenAPI(String endPoint, Map<String, String> uriVariables) {
        ResponseEntity<OpenApiResponse> response;
        OpenApiResponse openApiResponseResponse = null;
        try {
            response = restTemplate.exchange(endPoint, HttpMethod.GET, null, OpenApiResponse.class, uriVariables);
            openApiResponseResponse = response.getBody();
        } catch (HttpClientErrorException exception) {
            handleException(exception);
        }
        return openApiResponseResponse;
    }

    private void handleException(Exception exception) {
        log.error("Failed to call open weather map API, error message {}", exception.getMessage());
        throw new OpenWeatherMapException(exception);
    }

    private Map<String, String> formGeoLocationMap(Double latitude, Double longitude) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("latitude", String.valueOf(latitude));
        uriVariables.put("longitude", String.valueOf(longitude));
        return uriVariables;
    }
}
