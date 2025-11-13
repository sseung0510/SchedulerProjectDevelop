package com.schedulerprojectdevelop.domain.schedule.controller;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.schedule.model.request.CreateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.response.CreateScheduleResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.GetScheduleCommentResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.GetScheduleResponse;
import com.schedulerprojectdevelop.domain.schedule.model.response.UpdateScheduleResponse;
import com.schedulerprojectdevelop.domain.schedule.service.ScheduleService;
import com.schedulerprojectdevelop.domain.user.model.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponse> save(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser.getUserId(), request));
    }

    /**
     * 일정 전체 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }

    /**
     * 일정 단건 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleCommentResponse> findOne(
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
    @PutMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(sessionUser.getUserId(), scheduleId, request));
    }

    /**
     * 일정 삭제
     * @param scheduleId
     * @return
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        scheduleService.delete(sessionUser.getUserId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
