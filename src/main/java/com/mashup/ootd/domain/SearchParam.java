package com.mashup.ootd.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchParam {

    private Long id;
    private Long userId;
    private String photoUrl;
    private String address;
    private String weather;
    private String temperature;

}
