package com.mashup.ootd.domain.weathergeo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrentWeatherGeo {
    private String description;
    private Long apparentTemp;
    private Long windChillTemp;
    private Long maxTemp;
    private Long minTemp;
    private String location;
}
