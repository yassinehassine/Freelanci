package com.example.freelanci.gestionClient.repositories;

import com.example.freelanci.gestionClient.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByClientId(Long clientId);

    List<Job> findByClientIdAndCreatedAtBetween(Long clientId, LocalDateTime from, LocalDateTime to);

    List<Job> findByProjectStatus(Job.EtatProjet projectStatus);

}
