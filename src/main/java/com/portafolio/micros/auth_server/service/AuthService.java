package com.portafolio.micros.auth_server.service;

import com.portafolio.micros.auth_server.dtos.TokenDto;
import com.portafolio.micros.auth_server.dtos.UserDto;

public interface AuthService {
	
	TokenDto login(UserDto dto);

	TokenDto validateToken(TokenDto dto);
}
