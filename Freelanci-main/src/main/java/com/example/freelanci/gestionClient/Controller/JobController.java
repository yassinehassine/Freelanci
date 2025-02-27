package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Créer un job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobService.createJob(job);

        if (createdJob != null) {
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // L'utilisateur n'a pas été trouvé
        }
    }

    // Obtenir tous les jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    // Obtenir un job par ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long jobId) {
        return jobService.getJobById(jobId)
                .map(job -> new ResponseEntity<>(job, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Mettre à jour un job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long jobId, @RequestBody Job jobDetails) {
        Job updatedJob = jobService.updateJob(jobId, jobDetails);
        return updatedJob != null ? new ResponseEntity<>(updatedJob, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    // Supprimer un job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }
}
