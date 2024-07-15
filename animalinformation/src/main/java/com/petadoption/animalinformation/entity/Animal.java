package com.petadoption.animalinformation.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @Size(max = 255, message = "Special needs description must be less than 255 characters")
    private String specialNeeds;

    @NotBlank(message = "Adoption status is mandatory")
    @Enumerated(EnumType.STRING) 
    private AdoptionStatus adoptionStatus;

    @Size(max = 50, message = "Breed must be less than 50 characters")
    private String breed;

    @Size(max = 255, message = "Location must be less than 255 characters")
    private String location;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public  AdoptionStatus getAdoptionStatus() {
		return adoptionStatus;
	}

	public void setAdoptionStatus(AdoptionStatus adoptionStatus) {
		this.adoptionStatus = adoptionStatus;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
}
