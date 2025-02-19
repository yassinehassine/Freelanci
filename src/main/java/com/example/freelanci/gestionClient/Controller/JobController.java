package com.example.freelanci.gestionClient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.services.JobService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long jobId) {
        Optional<Job> job = jobService.getJobById(jobId);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long jobId, @RequestBody Job jobDetails) {
        Job updatedJob = jobService.updateJob(jobId, jobDetails);
        return updatedJob != null ? new ResponseEntity<>(updatedJob, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    // Delete a job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/completedProjects/{clientName}")
    public long getCompletedProjectsForClient(@PathVariable("clientName") String clientName) {
        return jobService.countCompletedProjectsForClient(clientName);
    }
}