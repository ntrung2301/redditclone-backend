package com.trungnguyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trungnguyen.model.Comment;
import com.trungnguyen.model.Post;
import com.trungnguyen.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

	List<Comment> findAllByUser(User user);

	List<Comment> findByPost(Post post);

}
