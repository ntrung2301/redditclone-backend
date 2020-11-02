package com.trungnguyen.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Generating getters and setters
@Entity
@Builder //Create object for a class
@AllArgsConstructor //Create parameterised constructor
@NoArgsConstructor	//Create non-parameterised constructor
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long postId;
	
	@NotBlank(message="Post name cannot be blank")
	private String postName;
	
	@Nullable
	private String url;
	@Nullable
	@Lob
	private String description;
	private Integer voteCount;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="userId", referencedColumnName="userId")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id", referencedColumnName="id")
	private Subreddit subreddit;
	
	private Instant createdDate;
}
