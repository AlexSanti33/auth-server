package com.portafolio.micros.auth_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication implements CommandLineRunner{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Password: "+this.bCryptPasswordEncoder.encode("secret"));		
	}

}
