package com.schedulerprojectdevelop.common.exception;

import lombok.Getter;

@Getter
public class MethodArgumentNotValidException extends CustomException{


    public MethodArgumentNotValidException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
