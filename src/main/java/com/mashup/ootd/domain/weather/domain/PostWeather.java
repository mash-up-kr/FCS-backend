package com.mashup.ootd.domain.weather.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostWeather {
    private String description;
    private String apparentTemp;
    private String windChillTemp;
    private String minTemp;
    private String maxTemp;
    private String precipitation;

    @Override
    public String toString() {
        return "PostWeather{" +
                "description='" + description + '\'' +
                ", apparentTemp='" + apparentTemp + '\'' +
                ", windChillTemp='" + windChillTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", precipitation='" + precipitation + '\'' +
                '}';
    }
}
