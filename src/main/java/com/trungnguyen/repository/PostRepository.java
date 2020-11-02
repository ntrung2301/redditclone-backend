package com.trungnguyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trungnguyen.model.Post;
import com.trungnguyen.model.Subreddit;
import com.trungnguyen.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findAllByUser(User user);
	
	
}
