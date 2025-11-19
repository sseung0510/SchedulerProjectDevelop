package com.schedulerprojectdevelop.domain.comment.model.response;

import lombok.Getter;

/**
 * 댓글 생성 Response
 */
@Getter
public class CreateCommentResponse {
    private final Long commentId;
    private final String content;
    private final Long userId;
    private final Long scheduleId;

    public CreateCommentResponse(Long commentId, String content, Long userId, Long scheduleId) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.scheduleId = scheduleId;
    }
}