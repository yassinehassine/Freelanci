package com.example.freelanci.gestionClient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    //@Autowired
    private JobRepository jobRepository;

    // Create a new job
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get a job by ID
    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findById(jobId);
    }

    // Update a job
    public Job updateJob(Long jobId, Job jobDetails) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            job.setTitle(jobDetails.getTitle());
            job.setDescription(jobDetails.getDescription());
            job.setBudget(jobDetails.getBudget());
            job.setCategory(jobDetails.getCategory());
            job.setCreatedAt(jobDetails.getCreatedAt());
            job.setDeadline(jobDetails.getDeadline());
            job.setClientName(jobDetails.getClientName());
            job.setProjectStatus(jobDetails.getProjectStatus());
            return jobRepository.save(job);
        }
        return null;
    }

    // Delete a job
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}