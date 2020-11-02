package com.trungnguyen.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data//Generating getters and setters
@Entity
@AllArgsConstructor //Create parameterised constructor
@NoArgsConstructor	//Create non-parameterised constructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank(message="Username cannot be blank")
	private String username;
	
	@NotBlank(message="Password cannot be blank")
	private String password;
	
	@Email
	@NotEmpty(message="Email is required")
	private String email;

	private Instant created;
	private boolean enabled;
}
