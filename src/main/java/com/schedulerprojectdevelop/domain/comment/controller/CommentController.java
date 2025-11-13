package com.schedulerprojectdevelop.domain.comment.controller;

import com.schedulerprojectdevelop.common.exception.CustomException;
import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import com.schedulerprojectdevelop.domain.comment.model.request.CreateCommentRequest;
import com.schedulerprojectdevelop.domain.comment.model.request.UpdateCommentRequest;
import com.schedulerprojectdevelop.domain.comment.model.response.CreateCommentResponse;
import com.schedulerprojectdevelop.domain.comment.model.response.UpdateCommentResponse;
import com.schedulerprojectdevelop.domain.comment.service.CommentService;
import com.schedulerprojectdevelop.domain.user.model.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(sessionUser.getUserId(), request, scheduleId));
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateCommentRequest request,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(sessionUser.getUserId(), request, scheduleId, commentId));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        if(sessionUser == null) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_LOGIN_REQUIRED);
        }
        commentService.deleteComment(sessionUser.getUserId(), scheduleId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}