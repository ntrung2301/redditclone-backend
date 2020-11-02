package com.trungnguyen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trungnguyen.dto.VoteDto;
import com.trungnguyen.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {
	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> voteForPost(@RequestBody VoteDto voteDto) {
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
