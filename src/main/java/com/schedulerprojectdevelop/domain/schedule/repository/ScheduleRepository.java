package com.schedulerprojectdevelop.domain.schedule.repository;

import com.schedulerprojectdevelop.common.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteByUser_Id(Long userId);

    @Query("SELECT s FROM Schedule s JOIN FETCH s.user")
    Page<Schedule> findAllScheduleWithPage(Pageable pageable);
}