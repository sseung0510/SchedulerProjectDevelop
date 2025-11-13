package com.schedulerprojectdevelop.domain.comment.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long commentId;
    private final String content;
    private final Long userId;
    private final LocalDateTime createAt;

    public GetCommentResponse(Long commentId, String content, Long userId, LocalDateTime createAt) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
    }
}
