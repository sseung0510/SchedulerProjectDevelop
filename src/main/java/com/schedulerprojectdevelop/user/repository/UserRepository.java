package com.schedulerprojectdevelop.user.repository;

import com.schedulerprojectdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
