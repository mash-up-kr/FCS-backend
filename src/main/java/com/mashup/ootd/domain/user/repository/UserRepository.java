package com.mashup.ootd.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mashup.ootd.domain.user.entity.AuthType;
import com.mashup.ootd.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUidAndAuthType(String uid, AuthType authType);

}
