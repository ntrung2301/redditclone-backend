package com.trungnguyen.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trungnguyen.dto.SubredditDto;
import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.mapper.SubredditMapper;
import com.trungnguyen.model.Subreddit;
import com.trungnguyen.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	
	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = subredditRepository.save(subredditMapper.dtoToSubreddit(subredditDto));
		subredditDto.setId(subreddit.getId());
		return subredditDto;
	}
	
	@Transactional
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
				.stream()
				.map(subredditMapper::subredditToDto)
				.collect(Collectors.toList());
	}
	
	
	@Transactional
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
						.orElseThrow(() -> new SpringRedditException("No subreddit found with the id " + id));
		return subredditMapper.subredditToDto(subreddit);
	}
}
