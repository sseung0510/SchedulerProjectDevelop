package com.schedulerprojectdevelop.schedule.service;

import com.schedulerprojectdevelop.schedule.dto.*;
import com.schedulerprojectdevelop.schedule.entity.Schedule;
import com.schedulerprojectdevelop.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     * @param request
     * @return
     */
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getUserName(), request.getScheduleTitle(), request.getScheduleContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getScheduleId(),
                savedSchedule.getUserName(),
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
                        schedule.getScheduleId(),
                        schedule.getUserName(),
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
                schedule.getScheduleId(),
                schedule.getUserName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent()
        );
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @param request
     * @return
     */
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("일정이 없습니다.")
        );

        schedule.updateSchedule(request.getScheduleTitle(), request.getScheduleContent());

        return new UpdateScheduleResponse(
                schedule.getScheduleId(),
                schedule.getUserName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 일정 삭제
     * @param scheduleId
     */
    @Transactional
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if(!existence) {
            throw new IllegalStateException("일정이 없습니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }




}
