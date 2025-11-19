package com.schedulerprojectdevelop.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 유저 정보 업데이트 Request
 */
@Getter
public class UpdateUserRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;
}
