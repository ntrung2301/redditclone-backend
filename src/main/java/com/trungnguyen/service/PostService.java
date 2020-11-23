package com.trungnguyen.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trungnguyen.dto.PostRequest;
import com.trungnguyen.dto.PostResponse;
import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.mapper.PostMapper;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.Subreddit;
import com.trungnguyen.model.User;
import com.trungnguyen.repository.PostRepository;
import com.trungnguyen.repository.SubredditRepository;
import com.trungnguyen.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
	
	private final SubredditRepository subredditRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostMapper postMapper;
	private final AuthService authService;
	
	@Transactional
	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SpringRedditException("No subreddit found with the given subreddit name " + postRequest.getSubredditName()));
		User user = authService.getCurrentUser();
		Post post = postMapper.requestToPost(postRequest, subreddit, user);
		postRepository.save(post);
		return post;
	}
	
	@Transactional
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("No post found with the given id " + id));
		return postMapper.postToResponse(post);
	}
	
	@Transactional
	public List<PostResponse> getAll() {
		return postRepository.findAll()
					.stream()
					.map(s -> postMapper.postToResponse(s))
					.collect(Collectors.toList());
	}

	@Transactional
	public List<PostResponse> getPostsBySubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with the given id "+id));
		return postRepository.findAllBySubreddit(subreddit)
							.stream()
							.map(posts -> postMapper.postToResponse(posts))
							.collect(Collectors.toList());
	}

	public List<PostResponse> getPostsByUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("No user found with the given id " + username));
		return postRepository.findAllByUser(user)
							.stream()
							.map(posts -> postMapper.postToResponse(posts))
							.collect(Collectors.toList());
	}
}
