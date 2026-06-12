package me.seonkyukim.springbootdeveloper.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Bean //정적 리소스에만 스프링 시큐리티의 기능을 사용하지 않게 설정
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
		 				   .requestMatchers(PathRequest.toH2Console())
		 				   .requestMatchers("/static/**");
	}
	
	@Bean // 특정 HTTP 요청에 웹 기반 보안을 구성함.
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
										  /* 
										   	 인증,인가 설정 (특정 경로에 대한 엑세스 설정을 함 
										     requestMatchers()  : 특정 요청과 일치하는 url에 대한 엑세스 설정함
										     permitAll()		: 누구나 접근이 가능하게 설정.
										   						  login, signup, user 로 요청이 오면 
										   						  인증, 인가 없이 접근이 가능함
					   						 anyRequest()		: 위에서 설정한 url 이외의 요청에 대해서 설정.
					   						 authenticated()	: 별도 인가는 필요하지 않지만 인증이 성공한 상태여야
					   						 					  접근이 가능하다
										    */
		return http.authorizeHttpRequests(auth -> auth.requestMatchers("/login","/signup","/user") 
													  .permitAll()
													  .anyRequest()
													  .authenticated()
											/*
											   폼 기반의 로그인 설정 <form>?
											   loginPage()         : 로그인 페이지 경로 설정
											   defaultSuccessUrl() : 로그인이 완료되었을 때 이동할 경로 설정
											   
											*/
										  ).formLogin(formLogin -> formLogin.loginPage("/login")
												  						    .defaultSuccessUrl("/articles", true)) // true 설정 없을때는 index.html 을 찾아갔었
											/* 
											   Log Out 설정
											   logoutSuccessUrl() 	  : 로그아웃이 완료되었을 때 이동할 경로 설정
											   invalidateHttpSession() : 로구아웃 후 세션 전체 삭제할지 여부 true, false
											 */
										   .logout(logout -> logout.logoutSuccessUrl("/login")
												  				  .invalidateHttpSession(true))
										    // CSRF 설정 비활성화. CSRF공격 방지 위해서는 enable 을 하지만
										    // 연습 용도로 비활성화함
										   .csrf(AbstractHttpConfigurer::disable)
										   .build();
	}
	
	// 패스워드 암호화 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
}
