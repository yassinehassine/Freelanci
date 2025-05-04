package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.dto.CreateJobRequest;
import com.example.freelanci.gestionClient.dto.JobResponse;
import com.example.freelanci.gestionClient.dto.UpdateJobDto;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.JobRepository;
import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import com.example.freelanci.gestionUser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService; // Pour récupérer l'utilisateur par ID
    @Autowired
    private UserRepository userRepository;

    public Job createJob(CreateJobRequest dto) {
        // 1. Find the client by ID
        Long id = dto.getClientId() != null ? dto.getClientId() : 1L;   // 👈 default
        User client = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));

        // 2. Build the Job entity
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setBudget(dto.getBudget());
        job.setCategory(dto.getCategory());
        job.setClient(client);

        // If your entity uses java.util.Date, convert from LocalDate if present
        if (dto.getCreatedAt() != null) {
            job.setCreatedAt(Date.from(dto.getCreatedAt()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()));
        }
        if (dto.getDeadline() != null) {
            job.setDeadline(Date.from(dto.getDeadline()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()));
        }

        // Convert string to enum if needed
        if (dto.getProjectStatus() != null) {
            try {
                Job.EtatProjet statusEnum = Job.EtatProjet.valueOf(dto.getProjectStatus());
                job.setProjectStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                // If invalid, set a default or throw an error
                job.setProjectStatus(Job.EtatProjet.NEST_PAS_DEBUTE);
            }
        } else {
            // Default
            job.setProjectStatus(Job.EtatProjet.NEST_PAS_DEBUTE);
        }

        // 3. Save and return
        return jobRepository.save(job);
    }


    // Obtenir tous les jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();  // Récupérer tous les jobs
    }

    // Obtenir un job par son ID
    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findById(jobId);  // Trouver un job par son ID
    }

    // Mettre à jour un job
    public Job updateJob(Long jobId, UpdateJobDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isEmpty()) {
            return null; // Or throw an exception
        }

        Job job = optionalJob.get();

        // If a field is provided, update it. Otherwise, keep old value.
        // (This is partial update logic. For a full update, just overwrite everything.)
        if (dto.getTitle() != null) {
            job.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            job.setDescription(dto.getDescription());
        }
        if (dto.getBudget() != 0) {
            job.setBudget(dto.getBudget());
        }
        if (dto.getCategory() != null) {
            job.setCategory(dto.getCategory());
        }
        if (dto.getCreatedAt() != null) {
            job.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getDeadline() != null) {
            job.setDeadline(dto.getDeadline());
        }
        if (dto.getProjectStatus() != null) {
            job.setProjectStatus(Job.EtatProjet.valueOf(dto.getProjectStatus()));
        }
        if (dto.getClientId() != null) {
            Optional<User> maybeUser = userService.getUserById(dto.getClientId());
            if (maybeUser.isPresent()) {
                job.setClient(maybeUser.get());
            } else {
                // You can decide how to handle the "user not found" scenario:
                // 1) Throw an exception
                // 2) Return null or an error code
                // 3) Log and ignore
                throw new RuntimeException("User with ID " + dto.getClientId() + " not found");
            }
        }

        return jobRepository.save(job);
    }

    // Supprimer un job par son ID
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);  // Supprimer le job en fonction de l'ID
    }

    public JobResponse toDto(Job job) {
        JobResponse dto = new JobResponse();
        dto.setId(job.getJobId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setBudget(job.getBudget());
        dto.setCategory(job.getCategory());
        dto.setClientId(job.getClient() != null ? job.getClient().getId() : null);
        dto.setCreatedAt(job.getCreatedAt());
        dto.setDeadline(job.getDeadline());
        dto.setProjectStatus(job.getProjectStatus().name());
        return dto;
    }

}
