package com.trungnguyen.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trungnguyen.dto.AuthenticateResponse;
import com.trungnguyen.dto.LoginRequest;
import com.trungnguyen.dto.RefreshTokenRequest;
import com.trungnguyen.dto.RegisterRequest;
import com.trungnguyen.service.AuthService;
import com.trungnguyen.service.RefreshTokenService;

import lombok.AllArgsConstructor;

/*
 *  @RestController - where RequestMapping assume ResponseBody semantics by default
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest rr) {
		authService.signUp(rr);
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account created successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticateResponse login(@RequestBody LoginRequest lr) {
		return authService.login(lr);
	}
	
	@PostMapping("/refresh-token")
	public AuthenticateResponse refresh(@Valid @RequestBody RefreshTokenRequest rtr) {
		return authService.refreshToken(rtr);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest rtr) {
		refreshTokenService.deleteRefreshToken(rtr.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token successfully deleted");
	}
	
}
