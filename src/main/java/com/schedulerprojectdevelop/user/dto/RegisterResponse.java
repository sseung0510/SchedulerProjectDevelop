package com.schedulerprojectdevelop.user.dto;

import lombok.Getter;

@Getter
public class RegisterResponse {
    private final Long userId;
    private final String userName;
    private final String userEmail;

    public RegisterResponse(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
