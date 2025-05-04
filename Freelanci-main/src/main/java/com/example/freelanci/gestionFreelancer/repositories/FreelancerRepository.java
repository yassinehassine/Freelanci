package com.example.freelanci.gestionFreelancer.repositories;

import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,Long> {
    @Query("SELECT f FROM Freelancer f ORDER BY RAND()")
    Freelancer findRandomFreelancer(); // Returns a random freelancer
}
