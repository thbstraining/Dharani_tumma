package com.petadoption.animalinformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AnimalinformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalinformationApplication.class, args);
	}

}
