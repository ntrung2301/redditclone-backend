package com.trungnguyen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data Transfer Object
 * Loosen the coupling between the API and model -> less data exposed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String email;
	private String username;
	private String password;
}
