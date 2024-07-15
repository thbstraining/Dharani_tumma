package com.petadoption.animalapplication.repository;


import com.petadoption.animalapplication.entity.AdoptionApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
}
