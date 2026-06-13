package me.seonkyukim.springbootdeveloper.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id", updatable = false)
	private Long id;
	
	@Column(name="userId", nullable = false, unique =true)
	private Long userId;
	
	@Column(name="refresh_token", nullable = false)
	private String refreshToken;
	
	// 롬복 에러로 생성자 자꾸 구현 안됨
	protected RefreshToken () {
		
	}
	
	public RefreshToken(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}
	
	public RefreshToken update(String newRefreshToken) {
		this.refreshToken = newRefreshToken;
		
		return this;
	}
}
