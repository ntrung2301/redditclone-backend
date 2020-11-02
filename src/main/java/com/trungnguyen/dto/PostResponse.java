package com.trungnguyen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Long id;
	private String postName;
	private String subredditName;
	private String description;
	private String url;
	private String username;
	
	private Integer voteCount;
	private Integer commentCount;
	private String duration;
}
