package com.schedulerprojectdevelop.domain.schedule.repository;

import com.schedulerprojectdevelop.common.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteByUser_Id(Long userId);
}
