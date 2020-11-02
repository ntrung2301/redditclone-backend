package com.trungnguyen.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Generating getters and setters
@Entity
@AllArgsConstructor //Create parameterised constructor
@NoArgsConstructor	//Create non-parameterised constructor
@Table(name="token")
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	@OneToOne(fetch=FetchType.LAZY)
	private User user;
	
	private Instant expiryDate;
}
