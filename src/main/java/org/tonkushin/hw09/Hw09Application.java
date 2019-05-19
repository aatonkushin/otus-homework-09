package org.tonkushin.hw09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class Hw09Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw09Application.class, args);
	}

}
