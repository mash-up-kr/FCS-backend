package com.mashup.ootd.domain.post.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.mashup.ootd.domain.style.domain.PostStyle;
import com.mashup.ootd.domain.user.entity.User;

import com.mashup.ootd.domain.weather.domain.PostWeather;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
@Entity
@Builder
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post")
	private final List<PostStyle> postStyles = new ArrayList<>();

	private String photoUrl;
	private String message;
	private String address;
	private String weather;
	private String temperature;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;


	@PrePersist
	private void onInit() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}


	@PreUpdate
	private void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	/*
	private void setPostWeather(PostWeather postweather) {
	}

	private void setLocation(Locaton location) {
	}
	 */
}
