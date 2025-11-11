package com.schedulerprojectdevelop.schedule.repository;

import com.schedulerprojectdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
