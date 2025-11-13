package com.schedulerprojectdevelop.domain.comment.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long commentId;
    private final String content;
    private final Long userId;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;


    public UpdateCommentResponse(Long commentId, String content, Long userId, LocalDateTime createAt, LocalDateTime updateAt) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
