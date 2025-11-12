package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

/**
 * 일정 수정 request
 */
@Getter
public class UpdateScheduleRequest {
    private String scheduleTitle;
    private String scheduleContent;

}
