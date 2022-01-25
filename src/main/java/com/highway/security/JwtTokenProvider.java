package com.highway.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.highway.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


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
	// Validate token from user
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(SecurityConstants.JWT_SECRET)
			.parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			// TODO: handle exception
			System.out.println("Invalid Signature.");
		}catch (MalformedJwtException e) {
			// TODO: handle exception
			System.out.println("Invalid Jwt token.");
		}catch (ExpiredJwtException e) {
			// TODO: handle exception
			System.out.println("Expired Jwt token");
		}catch (UnsupportedJwtException e) {
			// TODO: handle exception
			System.out.println("Unsupported Jwt token");
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.out.println("Jwt Claims String is empty.");
		}
		return false;
	}
	
	// Get userId from token
	public Long getUserIdFromToken(String token) {
		Claims claims= 	Jwts.parser()
				.setSigningKey(SecurityConstants.JWT_SECRET)
				.parseClaimsJws(token).getBody();
		System.out.println(claims.getId());
		String userId = (String)claims.get("id");
		return  Long.parseLong(userId);
	}
}
