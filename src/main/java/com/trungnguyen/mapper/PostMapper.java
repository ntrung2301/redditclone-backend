package com.trungnguyen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.trungnguyen.dto.PostRequest;
import com.trungnguyen.dto.PostResponse;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.Subreddit;
import com.trungnguyen.model.User;
import com.trungnguyen.repository.CommentRepository;

@Mapper(componentModel="spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Mapping(source="postRequest.description", target="description")
	@Mapping(source="subreddit", target="subreddit")
	@Mapping(source="user", target="user")
	@Mapping(target="createdDate", expression="java(java.time.Instant.now())")
	@Mapping(target="voteCount", constant="0") //Value should be 0 when saving a post
	public abstract Post requestToPost(PostRequest postRequest, Subreddit subreddit, User user);
	
	@Mapping(source="postId", target="id")
	@Mapping(source="postName", target="postName")
	@Mapping(source="url", target="url")
	@Mapping(source="description", target="description")
	@Mapping(source="subreddit.name", target="subredditName")
	@Mapping(source="user.username", target="username")
	@Mapping(target="commentCount", expression="java(commentCount(post))")
	@Mapping(target="duration", expression="java(getDuration(post))")
	public abstract PostResponse postToResponse(Post post);
	
	Integer commentCount(Post post) {return commentRepository.findByPost(post).size();};
	
	String getDuration(Post post) {return TimeAgo.using(post.getCreatedDate().toEpochMilli());}
}
