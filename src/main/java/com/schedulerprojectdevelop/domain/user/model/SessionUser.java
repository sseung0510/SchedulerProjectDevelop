package com.schedulerprojectdevelop.domain.user.model;

import lombok.Getter;

/**
 * 세션 정보
 */
@Getter
public class SessionUser {
    private final Long userId;
    private final String userEmail;

    public SessionUser(Long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
