package com.mashup.ootd.domain.jwt.service;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mashup.ootd.domain.exception.UnauthorizedException;
import com.mashup.ootd.domain.user.dto.SignInRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

	private String secretKey;
	
	@Value("${jwt.secret-key}")
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String createUserJwt(Long id) {
		String subject = "userAccessToken";
		
		Map<String, Object> payload = new HashMap<>();
		payload.put("id", id);
		
		return create(subject, payload);
	}
	
	public String create(String subject, Map<String, Object> payload) {
		Key key = Keys.hmacShaKeyFor(generateKey());

		String jws = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(subject)
				.setClaims(payload)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

		return jws;
	}
	
	public boolean isUsable(String jws) {
		log.info("jws : {} ", jws);
		getJwsClaims(jws);

		return true;
	}

	public Jws<Claims> getJwsClaims(String jws) {
		Jws<Claims> claims;

		try {
			claims = Jwts.parserBuilder()
						.setSigningKey(generateKey())
						.build()
						.parseClaimsJws(jws);
			
		} catch (Exception e) {
			throw new UnauthorizedException(UnauthorizedException.JWT_TOKEN_ERROR_MSG);
		}
		
		return claims;
	}
	
	public String getValue(String key) {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String jws = req.getHeader("Authorization");
		
		Jws<Claims> jwsClaims = getJwsClaims(jws);
		
		return jwsClaims.getBody().get(key).toString();
	}
	
	public String getUid() {
		return getValue("uid");
	}
	
	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = secretKey.getBytes("UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			log.debug("generate key fail : {} ", e);
		}

		return key;
	}


}