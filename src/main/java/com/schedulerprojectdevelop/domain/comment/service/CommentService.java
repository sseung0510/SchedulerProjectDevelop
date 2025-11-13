package com.schedulerprojectdevelop.domain.comment.service;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.comment.model.request.CreateCommentRequest;
import com.schedulerprojectdevelop.domain.comment.model.request.UpdateCommentRequest;
import com.schedulerprojectdevelop.domain.comment.model.response.CreateCommentResponse;
import com.schedulerprojectdevelop.common.entity.Comment;
import com.schedulerprojectdevelop.domain.comment.model.response.UpdateCommentResponse;
import com.schedulerprojectdevelop.domain.comment.repository.CommentRepository;
import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.domain.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.common.entity.User;
import com.schedulerprojectdevelop.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 생성
     * @param userId
     * @param request
     * @param scheduleId
     * @return CreateCommentResponse
     */
    @Transactional
    public CreateCommentResponse createComment(Long userId, CreateCommentRequest request, long scheduleId) {
        User user = findByUserId(userId);

        Schedule schedule = findByScheduleId(scheduleId);

        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId()
        );
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public UpdateCommentResponse updateComment(Long userId, @Valid UpdateCommentRequest request, Long scheduleId, Long commentId) {
        findByUserId(userId);
        findByScheduleId(scheduleId);

        Comment comment = findByCommentId(commentId);

        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN_UPDATE_NOT_AUTHOR);
        }

        comment.updateComment(request.getContent());

        return new UpdateCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long userId, Long scheduleId, Long commentId) {
        Comment comment = findByCommentId(commentId);
        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN_UPDATE_NOT_AUTHOR);
        }
        commentRepository.delete(comment);
    }

    // 회원 존재 여부
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
    }

    // 일정 존재 여부
    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_SCHEDULE)
        );
    }

    // 댓글 존재 여부
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT)
        );
    }



}
