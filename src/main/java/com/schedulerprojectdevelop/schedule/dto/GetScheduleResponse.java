package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

@Getter
public class GetScheduleResponse {
    private final Long scheduleId;
    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;

    public GetScheduleResponse(Long scheduleId, Long userId, String scheduleTitle, String scheduleContent) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
    }
}
