package com.revature.spoder_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.revature.spoder_app")
public class SpoderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpoderAppApplication.class, args);
	}

}
