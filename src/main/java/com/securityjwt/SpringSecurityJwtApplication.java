package com.securityjwt;

import com.securityjwt.models.ERoles;
import com.securityjwt.models.RoleEntity;
import com.securityjwt.models.UserEntity;
import com.securityjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner init(){
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("cesar720@gmail.com")
					.username("cesar")
					.password(passwordEncoder.encode("12345"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERoles.valueOf(ERoles.ADMIN.name()))
							.build()))
					.build();

			UserEntity userEntity2 = UserEntity.builder()
					.email("colesprouse@gmail.com")
					.username("cole")
					.password(passwordEncoder.encode("12mrrtt"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERoles.valueOf(ERoles.USER.name()))
							.build()))
					.build();

			UserEntity userEntity3 = UserEntity.builder()
					.email("lilireinhard@gmail.com")
					.username("lily")
					.password(passwordEncoder.encode("1qwe5"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERoles.valueOf(ERoles.INVITED.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}
}
