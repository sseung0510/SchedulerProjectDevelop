package com.schedulerprojectdevelop.domain.schedule.repository;

import com.schedulerprojectdevelop.common.entity.Schedule;
import com.schedulerprojectdevelop.domain.schedule.model.response.GetSchedulePageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteByUser_Id(Long userId);

    @Query("""
        SELECT new com.schedulerprojectdevelop.domain.schedule.model.response.GetSchedulePageResponse(
            s.title,
            s.content,
            COUNT(c.id),
            s.createdAt,
            s.modifiedAt,
            s.user.name
        )
        FROM Schedule s
        LEFT JOIN Comment c ON c.schedule = s
        GROUP BY s.title, s.content, s.createdAt, s.modifiedAt, s.user.name
        """)
    Page<GetSchedulePageResponse> findAllScheduleWithPage(Pageable pageable);
}