package com.example.TestDemoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.TestDemoApplication.Entity"})
public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestDemoApplication.class, args);
	}
}
