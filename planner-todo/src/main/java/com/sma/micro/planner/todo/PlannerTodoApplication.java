package com.sma.micro.planner.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.sma.micro.planner")
public class PlannerTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerTodoApplication.class, args);
	}

}
