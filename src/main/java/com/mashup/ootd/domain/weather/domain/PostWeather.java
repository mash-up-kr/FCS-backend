package com.mashup.ootd.domain.weather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWeather {
    private String description;
    private Long apparentTemp;
}
