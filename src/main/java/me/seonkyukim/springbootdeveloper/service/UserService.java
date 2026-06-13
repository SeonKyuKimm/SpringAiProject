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
	
	// JWT, 전달받은 user ID 로 user 를 검색, 전달하는 조회성 메서드
	public User findById(Long userId) {
		
		return userRepository.findById(userId)
				             .orElseThrow( () -> new IllegalArgumentException("Unexpected user"));
	}
}
