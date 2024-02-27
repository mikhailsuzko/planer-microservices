package com.sma.micro.planner.plannerusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.sma.micro.planner")
public class PlannerUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerUsersApplication.class, args);
	}

}
