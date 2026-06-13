package me.seonkyukim.springbootdeveloper.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.config.jwt.TokenProvider;
import me.seonkyukim.springbootdeveloper.domain.User;

@RequiredArgsConstructor
@Service
public class TokenService {
	
	private final TokenProvider tokenProv;
	private final RefreshTokenService refreshTokenService;
	private final UserService userService;
	
	public String createNewAccessToken(String refreshToken) {
		
		if(!tokenProv.validToken(refreshToken)) throw new IllegalArgumentException("Unexpected Token!!");
		
		Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
		User user = userService.findById(userId);
		
		return tokenProv.generateToken(user, Duration.ofHours(2));
	}
}
