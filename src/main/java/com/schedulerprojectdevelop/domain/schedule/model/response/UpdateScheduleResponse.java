package com.schedulerprojectdevelop.domain.schedule.model.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 수정 response
 */
@Getter
public class UpdateScheduleResponse {
    private final Long scheduleId;
    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long scheduleId, Long userId, String scheduleTitle, String scheduleContent, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
