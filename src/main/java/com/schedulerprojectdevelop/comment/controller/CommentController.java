package com.schedulerprojectdevelop.comment.controller;

import com.schedulerprojectdevelop.comment.dto.CreateCommentRequest;
import com.schedulerprojectdevelop.comment.dto.CreateCommentResponse;
import com.schedulerprojectdevelop.comment.service.CommentService;
import com.schedulerprojectdevelop.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param sessionUser
     * @param request
     * @param scheduleId
     * @return
     */
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateCommentRequest request,
            @PathVariable long scheduleId
    ) {
        if(sessionUser == null) {
            throw new IllegalArgumentException("로그인해주세요");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(sessionUser.getUserId(), request, scheduleId));
    }




}