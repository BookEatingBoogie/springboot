package com.bookEatingBoogie.dreamGoblin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class DreamGoblinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamGoblinApplication.class, args);
	}

}
