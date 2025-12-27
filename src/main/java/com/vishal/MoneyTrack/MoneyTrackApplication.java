package com.vishal.MoneyTrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoneyTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyTrackApplication.class, args);
	}

}
