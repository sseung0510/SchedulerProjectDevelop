package com.schedulerprojectdevelop.comment.repository;

import com.schedulerprojectdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySchedule_Id(Long scheduleId);
}
