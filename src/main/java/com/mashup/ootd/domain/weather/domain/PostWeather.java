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

    @Override
    public String toString() {
        return "PostWeather{" +
                "description='" + description + '\'' +
                ", apparentTemp='" + apparentTemp + '\'' +
                ", windChillTemp='" + windChillTemp + '\'' +
                '}';
    }
}
