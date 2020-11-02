package com.trungnguyen.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trungnguyen.dto.CommentDto;
import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.mapper.CommentMapper;
import com.trungnguyen.model.Comment;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.User;
import com.trungnguyen.repository.CommentRepository;
import com.trungnguyen.repository.PostRepository;
import com.trungnguyen.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	private final AuthService authService;
	private final UserRepository userRepository;
	
	@Transactional
	public void save(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new SpringRedditException("No post is found"));
		User user = authService.getCurrentUser();
		Comment comment = commentMapper.dtoToComment(commentDto, post, user);
		commentRepository.save(comment);
	}

	@Transactional
	public List<CommentDto> getAllCommentsByUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("No user found"));
		return commentRepository.findAllByUser(user).stream().map(comments -> commentMapper.commentToDto(comments)).collect(Collectors.toList());
	}

	
	@Transactional
	public List<CommentDto> getAllCommentsByPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new SpringRedditException("No post found"));
		return commentRepository.findByPost(post).stream().map(comments -> commentMapper.commentToDto(comments)).collect(Collectors.toList());
	}

}
