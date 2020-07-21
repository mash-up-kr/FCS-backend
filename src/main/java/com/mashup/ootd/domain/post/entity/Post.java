package com.mashup.ootd.domain.post.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
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
}
