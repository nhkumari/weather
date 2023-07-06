package com.weather.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.dto.OpenApiResponse;
import com.weather.dto.WeatherResponse;
import com.weather.service.WeatherService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static com.weather.integration.TextFixtures.API_FORECAST;
import static com.weather.integration.TextFixtures.API_WEATHER;
import static com.weather.integration.TextFixtures.EMPTY_WEATHER_REQUEST;
import static com.weather.integration.TextFixtures.INVALID_WEATHER_REQUEST;
import static com.weather.integration.TextFixtures.RESPONSE;
import static com.weather.integration.TextFixtures.WEATHER_REQUEST_WITH_CITY;
import static com.weather.integration.TextFixtures.WEATHER_REQUEST_WITH_LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WeatherService weatherService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void fetchWeatherForecast_when_address_provided() {

        var result = mockMvc.perform(post(API_FORECAST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(WEATHER_REQUEST_WITH_CITY)))
                .andExpect(status().isOk())
                .andReturn();
        OpenApiResponse openApiResponse = objectMapper.readValue(result.getResponse().getContentAsString(), OpenApiResponse.class);
        Assertions.assertEquals(40, openApiResponse.getList().size());
    }

    @Test
    @SneakyThrows
    void fetchWeatherForecast_when_geo_location_provided() {

        var result = mockMvc.perform(post(API_FORECAST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(WEATHER_REQUEST_WITH_LOCATION)))
                .andExpect(status().isOk())
                .andReturn();
        OpenApiResponse openApiResponse = objectMapper.readValue(result.getResponse().getContentAsString(), OpenApiResponse.class);
        Assertions.assertEquals(40, openApiResponse.getList().size());
    }

    @Test
    @SneakyThrows
    void fetchWeatherForecast_when_invalid_geo_location_provided() {
        mockMvc.perform(post(API_FORECAST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_WEATHER_REQUEST)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RESPONSE));
    }

    @Test
    @SneakyThrows
    void fetchWeatherForecast_when_empty_request_provided() {

        mockMvc.perform(post(API_FORECAST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPTY_WEATHER_REQUEST)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Either of location co-ordinates or city is mandatory, provided latitude 'null', longitude 'null', city 'null'"));
    }

    @Test
    @SneakyThrows
    void fetchCurrentWeather_when_geo_location_provided() {

        var result = mockMvc.perform(get(API_WEATHER, "51.5072", "0.1276"))
                .andExpect(status().isOk())
                .andReturn();
        WeatherResponse currentWeather = objectMapper.readValue(result.getResponse().getContentAsString(), WeatherResponse.class);
        Assertions.assertEquals(1, currentWeather.getWeather().size());
    }

    @Test
    @SneakyThrows
    void fetchCurrentWeather_when_invalid_geo_location_provided() {

        mockMvc.perform(get(API_WEATHER, "561.5072", "99.1276"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RESPONSE));
    }
}
