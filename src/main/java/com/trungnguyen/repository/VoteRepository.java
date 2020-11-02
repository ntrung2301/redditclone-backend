package com.trungnguyen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trungnguyen.model.Post;
import com.trungnguyen.model.User;
import com.trungnguyen.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
