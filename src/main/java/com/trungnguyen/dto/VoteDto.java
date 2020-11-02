package com.trungnguyen.dto;

import com.trungnguyen.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	private long postId;
	private VoteType voteType;
}
