package com.bookApp.bookApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookAppApplication implements CommandLineRunner {

	protected static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(BookAppApplication.class, args);
	}
	@Override
	public void run(final String... args){
		logger.info("BookApp command line - start");
	}
}
