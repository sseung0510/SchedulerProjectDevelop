package com.schedulerprojectdevelop.common.model;

import com.schedulerprojectdevelop.common.exception.ErrorMessage;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 오류 발생 응답 response DTO
 */
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String code;
    private final String message;


    public ErrorResponse(LocalDateTime timestamp, ErrorMessage errorCode, String message) {
        this.timestamp = timestamp;
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = message;
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String code, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
    }
}