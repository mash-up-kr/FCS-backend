package com.mashup.ootd.domain.post.dto;

import com.mashup.ootd.domain.post.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PostGetResponse {

    private Long id;
    private String photoUrl;
    private String address;
    private String weather;
    private String temperature;
    private LocalDateTime createAt;

    public PostGetResponse(Post entity){
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.address = entity.getAddress();
        this.weather = entity.getWeather();
        this.temperature = entity.getTemperature();
        this.createAt = entity.getCreatedAt();
    }

}
