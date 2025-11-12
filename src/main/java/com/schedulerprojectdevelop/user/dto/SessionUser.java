package com.schedulerprojectdevelop.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long userId;
    private final String userEmail;

    public SessionUser(Long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
