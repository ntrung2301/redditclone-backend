package com.trungnguyen.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.trungnguyen.dto.VoteDto;
import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.Vote;
import com.trungnguyen.model.VoteType;
import com.trungnguyen.repository.PostRepository;
import com.trungnguyen.repository.VoteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
	
	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;
	
	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new SpringRedditException("No post found with the id " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		/* Check whether the user has voted for this post yet */
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already vote " + voteDto.getVoteType() + "for this post");
		}
		
		if((VoteType.UPVOTE).equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
			log.info(post.getVoteCount().toString());
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		
		voteRepository.save(dtoToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote dtoToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}
