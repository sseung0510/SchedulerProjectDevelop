package com.schedulerprojectdevelop.user.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String userName;
    private String userEmail;
    private String userPassword;
}
