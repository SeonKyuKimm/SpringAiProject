package me.seonkyukim.springbootdeveloper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.dto.CreateAccessTokenReq;
import me.seonkyukim.springbootdeveloper.dto.CreateAccessTokenResp;
import me.seonkyukim.springbootdeveloper.service.TokenService;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
	
	private final TokenService service;
	
	@PostMapping("/api/token")
	public ResponseEntity<CreateAccessTokenResp> createNewAccessToken(@RequestBody CreateAccessTokenReq req) {
		String newAccessToken = service.createNewAccessToken(req.getRefreshToken()); 
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new CreateAccessTokenResp(newAccessToken));
	}
}
