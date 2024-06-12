package com.portafolio.micros.auth_server.service;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portafolio.micros.auth_server.dtos.TokenDto;
import com.portafolio.micros.auth_server.dtos.UserDto;
import com.portafolio.micros.auth_server.entities.UserEntity;
import com.portafolio.micros.auth_server.helpers.JwtHelper;
import com.portafolio.micros.auth_server.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;
	
	private final String USER_EXCEPTION_MESSAGE="Error to auth user";
	
	@Override
	public TokenDto login(UserDto dto) {
		final var userFromDB = this.userRepository.findByUsername(dto.getUsername())
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.SC_UNAUTHORIZED,USER_EXCEPTION_MESSAGE, null));
		this.validPassword(dto, userFromDB);
		return TokenDto.builder().accessToken(this.jwtHelper.createToken(userFromDB.getUsername())).build();
	}
	@Override
	public  TokenDto validateToken(TokenDto dto) {
		if(this.jwtHelper.validateToken(dto.getAccessToken())) {
			return TokenDto.builder().accessToken(dto.getAccessToken()).build();
		}
		throw new ResponseStatusException(HttpStatus.SC_UNAUTHORIZED,USER_EXCEPTION_MESSAGE, null);
	}
	
	private void validPassword(UserDto userDto, UserEntity userEntity) {
		if(!this.passwordEncoder.matches(userDto.getPassword(),userEntity.getPassword())) {
			throw new ResponseStatusException(HttpStatus.SC_UNAUTHORIZED,USER_EXCEPTION_MESSAGE, null);
		}
	}
	
}
