package com.portafolio.micros.auth_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.micros.auth_server.dtos.TokenDto;
import com.portafolio.micros.auth_server.dtos.UserDto;
import com.portafolio.micros.auth_server.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path ="auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@GetMapping(path="login")
	public ResponseEntity<TokenDto>jwtCreate(@RequestBody UserDto user){
		return ResponseEntity.ok(this.authService.login(user));
	}
	
	@PostMapping(path ="jwt")
	public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accesToken){
		return ResponseEntity.ok(this.authService.validateToken(TokenDto.builder().accessToken(accesToken).build()));
	}
	
	
	
	
	
	
	
}
