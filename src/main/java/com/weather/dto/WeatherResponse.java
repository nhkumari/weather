package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private  List<Weather> weather;
    private Temperature main;
    private String dt_txt;

}
