package com.trungnguyen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
	
	@Mapping(target="postId", expression="java(c.getPost().getPostId())")
	@Mapping(target="userName", expression="java(c.getUser().getUsername())")
	CommentDto commentToDto(Comment c);
}
