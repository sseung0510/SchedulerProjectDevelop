package com.schedulerprojectdevelop.domain.user.model.response;

import lombok.Getter;

@Getter
public class GetUserResponse {
    private final Long userId;
    private final String userName;
    private final String userEmail;

    public GetUserResponse(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
