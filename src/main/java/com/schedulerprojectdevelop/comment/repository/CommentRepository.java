package com.schedulerprojectdevelop.comment.repository;

import com.schedulerprojectdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
