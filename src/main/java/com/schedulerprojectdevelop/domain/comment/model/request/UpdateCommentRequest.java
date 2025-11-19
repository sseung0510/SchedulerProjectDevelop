package com.schedulerprojectdevelop.domain.comment.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 댓글 수정 request
 */
@Getter
public class UpdateCommentRequest {
    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 50, message = "50자 이하로 입력해주세요.")
    private String content;
}