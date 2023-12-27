package com.bookApp.bookApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.EventLogger;

@SpringBootApplication
public class BookAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookAppApplication.class, args);
	}

	@Override
	public void run(final String... args){
		System.out.println("\nBookApp command line");
		System.out.print("> ");


	}
}
