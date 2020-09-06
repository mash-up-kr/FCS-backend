package com.mashup.ootd.domain.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.mashup.ootd.domain.style.domain.PostStyle;
import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.user.entity.User;

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
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<PostStyle> postStyles = new ArrayList<>();

	private String photoUrl;
	
	private String message;
	private LocalDate date;
	private String address;
	private String weather;
	private Integer temperature;

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

	@Builder
	public Post(String photoUrl, String message, LocalDate date, String address, String weather, Integer temperature) {
		this.photoUrl = photoUrl;
		this.message = message;
		this.date = date;
		this.address = address;
		this.weather = weather;
		this.temperature = temperature;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void addStyles(List<Style> styles) {
		postStyles.addAll(
				styles.stream()
				.map(style -> PostStyle.of(this, style))
				.collect(Collectors.toList())
				);
	}
	
	public List<Long> getStyleIds() {
		return postStyles.stream()
				.map(postStyle -> postStyle.getStyle().getId())
				.collect(Collectors.toList());
	}
}
