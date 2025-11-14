package com.schedulerprojectdevelop.domain.schedule.controller;

import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.schedule.model.request.CreateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedulerprojectdevelop.domain.schedule.model.response.*;
import com.schedulerprojectdevelop.domain.schedule.service.ScheduleService;
import com.schedulerprojectdevelop.domain.user.model.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponse> save(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        checkedLogin(sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser.getUserId(), request));
    }

    /**
     * 일정 페이징 조회
     */
    @GetMapping
    public ResponseEntity<List<GetSchedulePageResponse>> findAllScheduleWithPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllScheduleWithPage(page-1, size));
    }

    /**
     * 일정 단건 조회 + 댓글 조회
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleCommentResponse> findOne(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    /**
     * 일정 수정
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        checkedLogin(sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(sessionUser.getUserId(), scheduleId, request));
    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        checkedLogin(sessionUser);
        scheduleService.delete(sessionUser.getUserId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인 체크
    public void checkedLogin(SessionUser sessionUser) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
    }
}