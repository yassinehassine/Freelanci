package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobFinishedService {

    @Autowired
    private JobRepository jobRepository;

    /**
     * Retrieves all jobs that are finished.
     * A finished job is defined as one with the projectStatus TERMINER.
     *
     * @return a list of finished jobs.
     */
    public List<Job> getFinishedJobs() {
        return jobRepository.findByProjectStatus(Job.EtatProjet.TERMINER);
    }
}
