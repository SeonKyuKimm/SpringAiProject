package me.seonkyukim.springbootdeveloper.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.User;
import me.seonkyukim.springbootdeveloper.dto.AddUserRequest;
import me.seonkyukim.springbootdeveloper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Long Save(AddUserRequest dto) {
				
		return userRepository.save(User.builder()
									   .email(dto.getEmail())
									   .password(passwordEncoder.encode(dto.getPassword()))
									   .build()).getId();
	}
}
