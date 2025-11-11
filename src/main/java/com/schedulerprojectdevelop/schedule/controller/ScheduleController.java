package com.schedulerprojectdevelop.schedule.controller;

import com.schedulerprojectdevelop.schedule.dto.*;
import com.schedulerprojectdevelop.schedule.service.ScheduleService;
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
    @PostMapping("/users/{userId}/scheduler")
    public ResponseEntity<CreateScheduleResponse> save(
            @PathVariable Long userId,
            @RequestBody CreateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(userId, request));
    }

    /**
     * 일정 전체 조회
     * @return
     */
    @GetMapping("/scheduler")
    public ResponseEntity<List<GetScheduleResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }

    /**
     * 일정 단건 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/scheduler/{scheduleId}")
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
    @PutMapping("/scheduler/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }

    /**
     * 일정 수정
     * @param scheduleId
     * @return
     */
    @DeleteMapping("/scheduler/{scheduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId
    ) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
