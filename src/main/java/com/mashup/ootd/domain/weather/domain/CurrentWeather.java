package com.mashup.ootd.domain.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
@Setter
public class CurrentWeather {
    private String description;
    private Long apparentTemp;
    private Long maxTemp;
    private Long minTemp;
}
