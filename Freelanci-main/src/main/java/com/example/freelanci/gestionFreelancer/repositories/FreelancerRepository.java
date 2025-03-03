package com.example.freelanci.gestionFreelancer.repositories;

import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,Long> {

}
