package com.schedulerprojectdevelop.schedule.controller;

import com.schedulerprojectdevelop.schedule.dto.*;
import com.schedulerprojectdevelop.schedule.service.ScheduleService;
import com.schedulerprojectdevelop.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param request
     * @return
     */
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> save(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody CreateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser.getUserId(), request));
    }

    /**
     * 일정 전체 조회
     * @return
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }

    /**
     * 일정 단건 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> findOne(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @param request
     * @return
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(sessionUser.getUserId(), scheduleId, request));
    }

    /**
     * 일정 삭제
     * @param scheduleId
     * @return
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        scheduleService.delete(sessionUser.getUserId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
