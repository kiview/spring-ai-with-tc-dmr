package dev.wittek.spring_ai_with_tc;

import org.springframework.boot.SpringApplication;

public class TestSpringAiWithTcApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringAiWithTcApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
