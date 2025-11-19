package com.schedulerprojectdevelop.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 유저 탈퇴 Request
 */
@Getter
public class DeleteUserRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;
}
