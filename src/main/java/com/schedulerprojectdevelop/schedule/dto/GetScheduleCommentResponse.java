package com.schedulerprojectdevelop.schedule.dto;

import com.schedulerprojectdevelop.comment.dto.GetCommentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class GetScheduleCommentResponse {
    private final Long scheduleId;
    private final Long userId;
    private final String scheduleTitle;
    private final String scheduleContent;
    private final List<GetCommentResponse> commentList;

    public GetScheduleCommentResponse(Long scheduleId, Long userId, String scheduleTitle, String scheduleContent, List<GetCommentResponse> commentList) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.commentList = commentList;
    }
}
