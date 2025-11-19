package com.schedulerprojectdevelop.domain.schedule.model.response;

import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 일정 조회 페이징 Response
 */
@Getter
public class GetSchedulePageResponse {
    private final String SchedulePageTitle;
    private final String SchedulePageContent;
    private final int commentCount;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetSchedulePageResponse(String schedulePageTitle, String schedulePageContent, int commentCount, LocalDateTime createAt, LocalDateTime modifiedAt, String userName) {
        SchedulePageTitle = schedulePageTitle;
        SchedulePageContent = schedulePageContent;
        this.commentCount = commentCount;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}