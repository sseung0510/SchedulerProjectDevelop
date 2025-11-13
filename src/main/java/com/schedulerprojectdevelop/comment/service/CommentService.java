package com.schedulerprojectdevelop.comment.service;

import com.schedulerprojectdevelop.comment.dto.CreateCommentRequest;
import com.schedulerprojectdevelop.comment.dto.CreateCommentResponse;
import com.schedulerprojectdevelop.comment.entity.Comment;
import com.schedulerprojectdevelop.comment.repository.CommentRepository;
import com.schedulerprojectdevelop.schedule.entity.Schedule;
import com.schedulerprojectdevelop.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.user.entity.User;
import com.schedulerprojectdevelop.user.repository.UserRepository;
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
//        if(userId == null) {
//            throw new IllegalArgumentException("로그인 해주세요");
//        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("일정이 존재하지 않습니다.")
        );

        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId()
        );
    }













}
