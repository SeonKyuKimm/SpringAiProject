package me.seonkyukim.springbootdeveloper.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.dto.AddUserRequest;
import me.seonkyukim.springbootdeveloper.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;
	
	@PostMapping("/user")
	public String signup(AddUserRequest req) {
		
		userService.Save(req);
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, 
						 HttpServletResponse resp) {
		new SecurityContextLogoutHandler().logout(
												  req, 
												  resp, 
												  SecurityContextHolder.getContext()
												  					   .getAuthentication()
							  					   										   );
		return "redirect:/login";
	}
}
