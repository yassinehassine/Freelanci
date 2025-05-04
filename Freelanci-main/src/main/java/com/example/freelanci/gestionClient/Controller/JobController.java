package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.dto.CreateJobRequest;
import com.example.freelanci.gestionClient.dto.JobResponse;
import com.example.freelanci.gestionClient.dto.UpdateJobDto;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.services.JobFilterService;
import com.example.freelanci.gestionClient.services.JobService;
import com.example.freelanci.gestionFreelancer.repositories.ProposalRepository;
import com.example.freelanci.gestionUser.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController {

    private final JobService jobService;
    private final ProposalRepository proposalRepository;
    private final JobFilterService jobFilterService;

    @Autowired
    public JobController(
            JobService jobService,
            ProposalRepository proposalRepository,
            JobFilterService jobFilterService
    ) {
        this.jobService = jobService;
        this.proposalRepository = proposalRepository;
        this.jobFilterService = jobFilterService;
    }

    // 🔹 CREATE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponse> createJob(@RequestBody CreateJobRequest dto) {
        try {
            Job created = jobService.createJob(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔹 READ ALL
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<Job> all = jobService.getAllJobs();
        List<JobResponse> dtos = all.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // 🔹 READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(@PathVariable("id") Long id) {
        Optional<Job> opt = jobService.getJobById(id);
        return opt.map(job -> ResponseEntity.ok(toDto(job)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 UPDATE
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable("id") Long id,
            @RequestBody UpdateJobDto dto
    ) {
        Job updated = jobService.updateJob(id, dto);
        return updated != null
                ? ResponseEntity.ok(toDto(updated))
                : ResponseEntity.notFound().build();
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 FREELANCERS FOR A JOB
    @GetMapping("/{jobId}/freelancers")
    public ResponseEntity<List<User>> getFreelancers(@PathVariable Long jobId) {
        List<User> list = proposalRepository.findFreelancersByJobId(jobId);
        return ResponseEntity.ok(list);
    }

    // 🔹 FILTER BY CLIENT & DATE
    @GetMapping("/user/{clientId}")
    public ResponseEntity<List<JobResponse>> getByClient(
            @PathVariable Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<Job> filtered = jobFilterService.getJobsByClientAndDate(clientId, from, to);
        List<JobResponse> dtos = filtered.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // 🔹 MAPPER (Job → JobResponse DTO)
    private JobResponse toDto(Job job) {
        JobResponse dto = new JobResponse();
        dto.setId(job.getJobId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setBudget(job.getBudget());
        dto.setCategory(job.getCategory());
        dto.setClientId(job.getClient() != null ? job.getClient().getId() : null);
        dto.setCreatedAt(job.getCreatedAt());
        dto.setDeadline(job.getDeadline());
        dto.setProjectStatus(job.getProjectStatus() != null ? job.getProjectStatus().name() : null);
        return dto;
    }
}
