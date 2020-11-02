package com.trungnguyen.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Generating getters and setters
@Entity
@AllArgsConstructor //Create parameterised constructor
@NoArgsConstructor	//Create non-parameterised constructor
@Builder // Create an instance of an object
public class Vote {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long voteId;
	
	private VoteType voteType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="postId", referencedColumnName="postId")
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", referencedColumnName="userId")
	private User user;
}
