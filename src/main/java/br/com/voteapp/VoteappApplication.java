package br.com.voteapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VoteappApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteappApplication.class, args);
	}

}
