package com.schedulerprojectdevelop.domain.comment.service;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.comment.model.request.CreateCommentRequest;
import com.schedulerprojectdevelop.domain.comment.model.response.CreateCommentResponse;
import com.schedulerprojectdevelop.common.entity.Comment;
import com.schedulerprojectdevelop.domain.comment.repository.CommentRepository;
import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.domain.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.common.entity.User;
import com.schedulerprojectdevelop.domain.user.repository.UserRepository;
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

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
    }

    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_SCHEDULE)
        );
    }












}
