package com.trungnguyen.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticateResponse {
	private String authenticationToken;
	private String username;
	private String refreshToken;
	private Instant expiresAt;
}
