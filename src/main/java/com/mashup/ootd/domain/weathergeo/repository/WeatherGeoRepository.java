package com.mashup.ootd.domain.weathergeo.repository;

import com.mashup.ootd.domain.weathergeo.domain.PostWeatherGeo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherGeoRepository extends JpaRepository<PostWeatherGeo,Long>{
}
