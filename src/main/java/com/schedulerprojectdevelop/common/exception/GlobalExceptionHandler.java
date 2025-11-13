package com.schedulerprojectdevelop.common.exception;

import com.schedulerprojectdevelop.common.model.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    // ResponseStatusException 발생 시 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(CustomException e) {
        log.error("CustomException 발생 {} : ", e.getMessage());

        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        e.getErrorMessage(),
                        e.getMessage())
                );
    }

//    // 유효성 검사 실패 시 처리 (@Valid 관련 예외)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatusException(MethodArgumentNotValidException ex, WebRequest request) {
//
//        String message = "Validation failed";
//
//        FieldError fieldError = ex.getBindingResult().getFieldError();
//        if(fieldError != null) {
//            message = fieldError.getDefaultMessage();
//        }
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getStatusCode().value(),
//                ex.getStatusCode().toString(),
//                message,
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
//    }
}
