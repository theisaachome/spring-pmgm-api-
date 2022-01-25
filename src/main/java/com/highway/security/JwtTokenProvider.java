package com.highway.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.highway.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider {

	// generate token

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expiredDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
		String userId = Long.toString(user.getId());
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());

		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
				.compact();

	}
}
