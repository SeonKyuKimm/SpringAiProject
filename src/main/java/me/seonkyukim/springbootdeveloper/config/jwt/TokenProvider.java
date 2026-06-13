package me.seonkyukim.springbootdeveloper.config.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.User;

@RequiredArgsConstructor
@Service
public class TokenProvider {

	private final JwtProperties jwt;
	
	public String generateToken(User user, Duration expiredAt) {
		
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}
	
	// Token 생성 메서드
	private String makeToken(Date expiry, User user) {
		
		Date now = new Date();
		
		return Jwts.builder()
				   // Header type : JWT
				   .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				   // 내용 issuer : application.yml 파일에서 설정한 값
				   .setIssuer(jwt.getIssuer())
				   .setIssuedAt(now)			// iAt : 현재 시간
				   .setExpiration(expiry)       // exp : expiry 멤버 변수값
				   .setSubject(user.getEmail()) // sub : 유저의 이메일
				   .claim("id", user.getId())   // 클레임 id : 유저의 아이디로
				   // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화함
				   .signWith(SignatureAlgorithm.HS256, jwt.getSecretKey())
				   .compact();
	}
	
	/*
	  JWT 토큰 유효성 검증 메서드
	  .yml 파일에 설정한
	  jwt:
        issuer: ajufresh@gmail.com    # 이슈 발급자
        secret_key: study-springboot  # 비밀키
      비밀값과 함께 토큰 복호화를 진행함. if 복호화과정에서 에러가 발생하면 유효하지 않은 토큰이므로 false 반환
	
	*/
	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwt.getSecretKey())
				.parseClaimsJws(token);
				
			return true;
		} catch(Exception e) {
			// 복호화 과정에서 error , 유효하지 않은 토큰
			return false;
		}
	}
	
	/* 
	  token 기반으로 인증 정보를 가져오는 메서드
	  
	  토큰을 받아 인증 정보를 담은 객체 Authentication을 반환해줌
      properties 에 비밀값으로 토큰을 복호화한 뒤, 클레임을 가져오는  private 메서드인
      getClaims를 호출, 클레임 정보를 반환받아 사용자 이메일이 들어있는 토큰제목 sub 와 토큰기반으로 인증정보 생성
      
      return 하는 new User 는 해당 프로젝트의 User 파일이 아닌, spring security 에서 제공하는 객체인 User 클래스에서 사용
	*/
	public Authentication getAuthentication(String token) {
		
		Claims claims = getClaims(token);
		
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
																											  claims.getSubject(), 
																											  "", 
																											  authorities
																											  ), 
				                                                    token
				                                                    ,authorities);
	}
	
	/* 
	  token 기반으로 user Id 가져오는 메서드
	  
	  프로퍼티즈 파일에 저장한 비밀값으로 토큰을 복호화,
	  클레임을 가져오는 private Claims getClaims 를 호출, 클레임 정보반환하고
	  클레임에서 id 키로 저장된 값을 가져와 반환함
	*/
	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id", Long.class);
	}
	
	private Claims getClaims(String token) {
		return Jwts.parser() // 클레임 조회
				   .setSigningKey(jwt.getSecretKey())
				   .parseClaimsJws(token)
				   .getBody();
	}
}
