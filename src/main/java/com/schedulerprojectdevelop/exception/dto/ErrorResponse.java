package com.schedulerprojectdevelop.exception.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 오류 발생 응답 response DTO
 */
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}