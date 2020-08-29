package com.mashup.ootd.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mashup.ootd.domain.style.domain.UserStyle;

public interface UserStyleRepository extends JpaRepository<UserStyle, Long> {

}
