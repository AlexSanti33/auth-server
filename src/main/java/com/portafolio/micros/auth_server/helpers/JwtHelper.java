package com.portafolio.micros.auth_server.helpers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtHelper {

	@Value("${application.jwt.secret}")
	private String jwtSecret;
	
	public String createToken(String userName) {
		final var now = new Date();
		var expirationDate = new Date(now.getTime()+(3600*1000));
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expirationDate)
				.signWith(getSecretKey()).compact();
	}
	
	public boolean validateToken(String token) {
		try {
			final var expirationDate = this.getExpirationDate(token);
			return expirationDate.after(new Date());
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.SC_UNAUTHORIZED,"jwt invlid", null);
		}
		
	}
	
	private Date getExpirationDate(String token) {
		return this.getClaimsFromToken(token, Claims::getExpiration);
	}
	
	private <T>T getClaimsFromToken(String token, Function<Claims, T>resolver){
		return resolver.apply(this.signTokens(token));
	}
	
	private Claims signTokens(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(this.getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private SecretKey getSecretKey() {
		
		return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
	}
	
}
