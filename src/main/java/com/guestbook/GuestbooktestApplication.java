package com.guestbook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuestbooktestApplication {

	private static Logger  logger = LogManager.getLogger(GuestbooktestApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(GuestbooktestApplication.class, args);
		logger.info("GuestbooktestApplication", () -> "Main Method");
	}

}
