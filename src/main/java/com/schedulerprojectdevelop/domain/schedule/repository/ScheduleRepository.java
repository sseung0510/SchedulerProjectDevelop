package com.schedulerprojectdevelop.domain.schedule.repository;

import com.schedulerprojectdevelop.common.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, PagingAndSortingRepository<Schedule, Long> {
    void deleteByUser_Id(Long userId);

    // 페이징 처리
    @Query(value = "SELECT * FROM schedules ORDER BY modified_at DESC LIMIT :limit OFFSET :offSet"
            , nativeQuery = true)
    List<Schedule> getProdustWithCustomQuery(@Param("limit") int limit,
                                             @Param("offSet") int offSet);
}
