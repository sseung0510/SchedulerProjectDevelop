package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

/**
 * 일정 생성 request
 */
@Getter
public class CreateScheduleRequest {
    private String scheduleTitle;
    private String scheduleContent;
}
