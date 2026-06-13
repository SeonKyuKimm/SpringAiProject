package me.seonkyukim.springbootdeveloper.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.RefreshToken;
import me.seonkyukim.springbootdeveloper.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepo;
	
	//Refresh token 으로 refresh token 객체를 검색해서 전달하는 메서드 
	public RefreshToken findByRefreshToken(String refreshToken) {
		
		return refreshTokenRepo.findByRefreshToken(refreshToken)
							   .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
	}
}
