package com.mashup.ootd.domain.weathergeo.domain;

import com.mashup.ootd.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@AllArgsConstructor
@Getter
@Entity
public class PostWeatherGeo {
    @JoinColumn(name = "post_id")
    private Post post;

    private String description;
    private Long apparentTemp;
    private Long windChillTemp;
    private String location;
}
