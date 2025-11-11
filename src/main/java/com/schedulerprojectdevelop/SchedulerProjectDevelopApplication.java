package com.schedulerprojectdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class SchedulerProjectDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerProjectDevelopApplication.class, args);
    }

}
