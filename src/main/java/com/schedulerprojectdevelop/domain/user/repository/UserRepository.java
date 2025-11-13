package com.schedulerprojectdevelop.domain.user.repository;

import com.schedulerprojectdevelop.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail); // 추가

    boolean existsByEmail(String userEmail);
}
