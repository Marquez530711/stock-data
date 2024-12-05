package com.ruxin.sd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StockDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockDataApplication.class, args);
	}

}
