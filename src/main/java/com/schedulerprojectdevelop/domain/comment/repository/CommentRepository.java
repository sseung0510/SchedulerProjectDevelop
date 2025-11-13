package com.schedulerprojectdevelop.domain.comment.repository;

import com.schedulerprojectdevelop.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySchedule_Id(Long scheduleId);
}
