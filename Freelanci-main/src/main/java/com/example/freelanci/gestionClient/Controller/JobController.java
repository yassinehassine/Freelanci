package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.dto.CreateJobRequest;
import com.example.freelanci.gestionClient.dto.JobResponse;
import com.example.freelanci.gestionClient.dto.UpdateJobDto;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.services.JobFilterService;
import com.example.freelanci.gestionClient.services.JobService;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import com.example.freelanci.gestionFreelancer.repositories.FreelancerRepository;
import com.example.freelanci.gestionFreelancer.repositories.ProposalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final String FILE_DIRECTORY = "src/main/resources/";
    @Autowired
    private FreelancerRepository freelancerRepository;


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
    public ResponseEntity<List<FreelancerDto>> getFreelancers(@PathVariable Long jobId) {
        List<Long> freelancerIds = proposalRepository.findFreelancerIdsByJobId(jobId);

        List<Freelancer> freelancers = freelancerRepository.findByIds(freelancerIds);

        List<FreelancerDto> dtos = freelancers.stream()
                .map(f -> new FreelancerDto(
                        f.getIdFreelancer(),
                        f.getSkills(),
                        f.getDomain(),
                        f.getViews(),
                        f.getDocumentPath()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
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

    @GetMapping("/files/{documentName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String documentName) {
        try {


            // Path to the directory where PDFs are stored (inside resources folder)
            String baseDirectory = "C:/Users/yassine/Desktop/freelanci/Freelanci-main/src/main/resources/";  // Adjust this based on where you store your files
            Path filePath = Paths.get(baseDirectory, documentName).normalize();  // Resolve the full file path

            // Create a resource from the file
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_PDF)  // Set content type as PDF
                        .body(resource);  // Return the resource (file content)
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if file doesn't exist
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Handle errors
        }
    }


}
