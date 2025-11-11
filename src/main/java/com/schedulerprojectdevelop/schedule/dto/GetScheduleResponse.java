package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

@Getter
public class GetScheduleResponse {
    private final Long scheduleId;
    private final String userName;
    private final String scheduleTitle;
    private final String scheduleContent;

    public GetScheduleResponse(Long scheduleId, String userName, String scheduleTitle, String scheduleContent) {
        this.scheduleId = scheduleId;
        this.userName = userName;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
    }
}
