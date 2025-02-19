package com.example.freelanci.gestionClient.repositories;

import com.example.freelanci.gestionClient.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, Long> {
    long countByClientNameAndProjectStatus(String clientName, Job.EtatProjet projectStatus);
}
