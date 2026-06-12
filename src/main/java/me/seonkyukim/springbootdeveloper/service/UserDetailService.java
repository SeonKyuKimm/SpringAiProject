package me.seonkyukim.springbootdeveloper.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String email) {
		return userRepository.findByEmail(email)
				             .orElseThrow( () -> new IllegalArgumentException((email)));
	}
}
