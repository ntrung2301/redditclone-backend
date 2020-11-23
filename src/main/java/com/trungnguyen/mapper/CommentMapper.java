package com.trungnguyen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.trungnguyen.dto.CommentDto;
import com.trungnguyen.model.Comment;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.User;

@Mapper(componentModel="spring")
public interface CommentMapper {

	@Mapping(source="cdto.text", target="text")
	@Mapping(source="cdto.id", target="commentId" )
	@Mapping(target="createdDate", expression="java(java.time.Instant.now())")
	@Mapping(source="post",target="post")
	@Mapping(source="user",target="user")
	Comment dtoToComment(CommentDto cdto, Post post, User user);
	
	default String getDuration(Comment comment) {return TimeAgo.using(comment.getCreatedDate().toEpochMilli());}

	
	@Mapping(target="postId", expression="java(c.getPost().getPostId())")
	@Mapping(target="userName", expression="java(c.getUser().getUsername())")
	@Mapping(target="duration", expression="java(getDuration(c))")
	CommentDto commentToDto(Comment c);
	
}
