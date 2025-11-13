package com.schedulerprojectdevelop.comment.dto;

import lombok.Getter;

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
