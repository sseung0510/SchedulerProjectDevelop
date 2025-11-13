package com.schedulerprojectdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 50, message = "50자 이하로 입력해주세요.")
    private String content;
}
