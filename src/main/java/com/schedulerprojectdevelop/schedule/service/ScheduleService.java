package com.schedulerprojectdevelop.schedule.service;

import com.schedulerprojectdevelop.schedule.dto.*;
import com.schedulerprojectdevelop.schedule.entity.Schedule;
import com.schedulerprojectdevelop.schedule.repository.ScheduleRepository;
import com.schedulerprojectdevelop.user.entity.User;
import com.schedulerprojectdevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public CreateScheduleResponse save(Long userId, CreateScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("없는 유저입니다.")
        );

        Schedule schedule = new Schedule(request.getScheduleTitle(), request.getScheduleContent(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getScheduleTitle(),
                savedSchedule.getScheduleContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    /**
     * 일정 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getScheduleTitle(),
                        schedule.getScheduleContent()
                )).toList();
    }

    /**
     * 일정 단건 조회
     * @param scheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 없습니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent()
        );
    }

    /**
     * 일정 수정
     */
    @Transactional
    public UpdateScheduleResponse update(Long userId, Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 없습니다.")
        );

        if(!schedule.getUser().getId().equals(userId)) {
            throw new IllegalStateException("작성자만 수정 가능합니다");
        }

        schedule.updateSchedule(request.getScheduleTitle(), request.getScheduleContent());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 삭제
     */
    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 없습니다.")
        );

        if(!schedule.getUser().getId().equals(userId)) {
            throw new IllegalStateException("작성자만 수정 가능합니다");
        }

        scheduleRepository.deleteById(scheduleId);
    }




}
