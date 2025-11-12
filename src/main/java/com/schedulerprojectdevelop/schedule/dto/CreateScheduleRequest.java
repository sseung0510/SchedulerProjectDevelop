package com.schedulerprojectdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 생성 request
 */
@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 30, message = "30자 이하로 입력해주세요.")
    private String scheduleTitle;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 200, message = "200자 이하로 입력해주세요.")
    private String scheduleContent;
}
