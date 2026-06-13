package me.seonkyukim.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenReq {
	
	private String refreshToken;
}
