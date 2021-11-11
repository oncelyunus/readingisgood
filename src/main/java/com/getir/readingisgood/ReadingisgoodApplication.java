package com.getir.readingisgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(
	scanBasePackages = {"com.getir"}
)
public class ReadingisgoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingisgoodApplication.class, args);
	}

}
