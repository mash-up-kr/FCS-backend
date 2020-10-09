package com.mashup.ootd.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.mashup.ootd.domain.style.domain.Style;
import com.mashup.ootd.domain.style.domain.UserStyle;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uid;
	@Enumerated(EnumType.STRING)
	private AuthType authType;

	private String nickname;
	
	private String profileImageUrl;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserStyle> userStyles = new ArrayList<>();

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
	public User(String uid, String authType, String nickname, String profileImageUrl) {
		this.uid = uid;
		this.authType = AuthType.valueOf(authType);
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
	
	public void setStyles(List<Style> styles) {
		userStyles.clear();
		userStyles.addAll(
				styles.stream()
					.map(style -> UserStyle.of(this, style))
					.collect(Collectors.toList())
				); 
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<Long> getStyleIds() {
		return userStyles.stream()
				.map(userStyle -> userStyle.getStyle().getId())
				.collect(Collectors.toList());
	}

}
