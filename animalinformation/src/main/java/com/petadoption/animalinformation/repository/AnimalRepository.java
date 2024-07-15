package com.petadoption.animalinformation.repository;

import com.petadoption.animalinformation.entity.Animal;

import feign.Param;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
	
	 @Query("SELECT a FROM Animal a WHERE (:breed IS NULL OR a.breed = :breed) " +
	           "AND (:age IS NULL OR a.age = :age) " +
	           "AND (:location IS NULL OR a.location = :location)")
	    List<Animal> searchAnimals(@Param("breed") String breed,
	                               @Param("age") Integer age,
	                               @Param("location") String location);
	}