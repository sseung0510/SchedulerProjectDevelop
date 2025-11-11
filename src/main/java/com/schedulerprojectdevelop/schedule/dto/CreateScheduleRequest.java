package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String scheduleTitle;
    private String scheduleContent;
}
