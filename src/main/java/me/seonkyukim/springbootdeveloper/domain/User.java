package me.seonkyukim.springbootdeveloper.domain;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Table(name = "users")
//@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", updatable = false)
	private Long id;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name ="password")
	private String password;
	
	@Builder
	public User(String email, String password, String auth) {
		this.email=email;
		this.password = password;
	}
	// @NoArgsConstructor 가 자꾸 에러를 일으켜서 생성자 이거로 대체함 
	protected User() {}
	@Override //권한 반환용
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}
	
	@Override // 사용자  id 컬럼 반환 ( 고유값 )
	public String getUsername() {
		return email;
	}
	
	@Override //use pw return
	public String getPassword() {
		return password;
	}
	
	@Override // 계정 만료 여부 방황
	public boolean isAccountNonExpired() {
		return true; // true -> 만료안됨
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true; // 계정 잠금 여부 반환 -> ㅇ잠금되지않음
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 패스워드 만료여부 -> true, 만료안됨
	}
	
	@Override
	public boolean isEnabled() {
		return true; // 계정 사용 가능 여부 -> true 사용가능
	}
}
