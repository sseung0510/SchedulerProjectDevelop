package com.schedulerprojectdevelop.domain.schedule.service;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.comment.model.response.GetCommentResponse;
import com.schedulerprojectdevelop.common.entity.Comment;
import com.schedulerprojectdevelop.domain.comment.repository.CommentRepository;
import com.schedulerprojectdevelop.domain.schedule.model.request.CreateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.response.CreateScheduleResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.GetScheduleCommentResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.GetScheduleResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.UpdateScheduleResponse;
import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.domain.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.common.entity.User;
import com.schedulerprojectdevelop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 일정 생성
     */
    @Transactional
    public CreateScheduleResponse save(Long userId, CreateScheduleRequest request) {
        User user = findByUserId(userId);

        Schedule schedule = new Schedule(request.getScheduleTitle(), request.getScheduleContent(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    /**
     * 일정 전체 조회
     */
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getTitle(),
                        schedule.getContent()
                )).toList();
    }

    /**
     * 일정 단건 조회 + 댓글 조회
     */
    @Transactional(readOnly = true)
    public GetScheduleCommentResponse findOne(Long scheduleId) {
        Schedule schedule = findByScheduleId(scheduleId);

        List<Comment> comments = commentRepository.findBySchedule_Id(scheduleId);
        List<GetCommentResponse> commentList = comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                    comment.getContent(),
                    comment.getUser().getId(),
                    comment.getCreatedAt()
                )).toList();

        return new GetScheduleCommentResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                commentList
        );
    }

    /**
     * 일정 수정
     */
    @Transactional
    public UpdateScheduleResponse update(Long userId, Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = findByScheduleId(scheduleId);
        matchedAuthor(userId, schedule.getUser().getId());
        schedule.updateSchedule(request.getScheduleTitle(), request.getScheduleContent());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 삭제
     */
    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = findByScheduleId(scheduleId);
        matchedAuthor(userId, schedule.getUser().getId());

        commentRepository.deleteBySchedule_Id(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    // 회원 확인
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
    }

    // 일정 확인
    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_SCHEDULE)
        );
    }

    // 작성자 일치 확인
    public void matchedAuthor(Long userId, Long commentUserId) {
        if(!userId.equals(commentUserId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN_ONLY_AUTHOR);
        }
    }
}