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

import com.trungnguyen.dto.PostRequest;
import com.trungnguyen.dto.PostResponse;
import com.trungnguyen.model.Post;
import com.trungnguyen.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postRequest));
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getSubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));	
	}
	
	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("by-user/{username}")
	public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(username));
	}
	
}
