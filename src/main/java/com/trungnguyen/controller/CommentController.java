package com.trungnguyen.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trungnguyen.dto.CommentDto;
import com.trungnguyen.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment) {
		commentService.save(comment);
		return new ResponseEntity<>(HttpStatus.CREATED);	
	}
	
	@GetMapping("/by-user/{username}")
	public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByUser(username));	
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByPost(postId));	
	}	
}
