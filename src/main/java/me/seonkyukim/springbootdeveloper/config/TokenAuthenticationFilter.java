package me.seonkyukim.springbootdeveloper.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.config.jwt.TokenProvider;

// JWT 토큰 필터 config class
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter{
	
	private final TokenProvider tokenProv;
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX         = "Bearer ";

	@Override
	protected void doFilterInternal( 
						HttpServletRequest  req  ,
						HttpServletResponse resp ,
						FilterChain		 chain ) throws ServletException, IOException {
		
		String authorizationHeader = req.getHeader(HEADER_AUTHORIZATION);
		String token = getAccessToken(authorizationHeader);
		if(tokenProv.validToken(token)) {
			Authentication auth = tokenProv.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		chain.doFilter(req, resp);
	}
	
	private String getAccessToken(String authorizationHeader) {
		
		if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length());
		}
		
		return null;
	}
}
