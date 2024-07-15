package com.petadoption.animalapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AnimalapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalapplicationApplication.class, args);
	}

}
