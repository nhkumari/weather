package com.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    private Double latitude;
    private Double longitude;
    private String city;
}
