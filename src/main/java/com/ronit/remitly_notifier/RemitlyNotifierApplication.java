package com.ronit.remitly_notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RemitlyNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemitlyNotifierApplication.class, args);
	}

}
