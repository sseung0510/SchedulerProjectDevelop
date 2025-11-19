package com.schedulerprojectdevelop.domain.schedule.service;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.comment.repository.CommentRepository;
import com.schedulerprojectdevelop.domain.schedule.model.request.CreateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.response.*;
import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.domain.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.common.entity.User;
import com.schedulerprojectdevelop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CreateScheduleResponse createSchedule(Long userId, CreateScheduleRequest request) {
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
     * 페이징 추가
     */
    @Transactional(readOnly = true)
    public Page<GetSchedulePageResponse> findAllScheduleWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());

        Page<Schedule> schedules = scheduleRepository.findAllScheduleWithPage(pageable);

        return schedules.map(schedule -> new GetSchedulePageResponse(
                schedule.getTitle(),
                schedule.getContent(),
                commentRepository.countBySchedule_Id(schedule.getId()),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getUser().getName()
        ));
    }

    /**
     * 일정 단건 조회
     */
    @Transactional(readOnly = true)
    public GetScheduleResponse findOneSchedule(Long scheduleId) {
        Schedule schedule = findByScheduleId(scheduleId);

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent()
        );
    }

    /**
     * 일정 수정
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long userId, Long scheduleId, UpdateScheduleRequest request) {
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
    public void deleteSchedule(Long userId, Long scheduleId) {
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