package com.schedulerprojectdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {
    private final Long scheduleId;
    private final String userName;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public CreateScheduleResponse(Long scheduleId, String userName, String scheduleTitle, String scheduleContent, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.userName = userName;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
