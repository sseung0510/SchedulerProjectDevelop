package com.schedulerprojectdevelop.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String userEmail;
    private String userPassword;
}
