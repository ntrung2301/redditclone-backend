package com.trungnguyen.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trungnguyen.dto.SubredditDto;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.Subreddit;

@Mapper(componentModel="spring")
public interface SubredditMapper {
	
	@Mapping(target="numOfPosts", expression="java(mapPosts(subreddit.getPosts()))")
	SubredditDto subredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numOfPosts) {return numOfPosts.size(); }
	
	@InheritInverseConfiguration
	@Mapping(target="posts", ignore=true)
	Subreddit dtoToSubreddit(SubredditDto subredditDto);
}
