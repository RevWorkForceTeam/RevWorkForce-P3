package com.rev.performance_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@org.springframework.boot.autoconfigure.domain.EntityScan("com.rev.performance_service.entity")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.rev.performance_service.repository")
public class PerformanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceServiceApplication.class, args);
	}

}
