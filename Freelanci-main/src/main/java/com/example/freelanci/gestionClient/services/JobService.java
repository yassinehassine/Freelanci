package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.JobRepository;
import com.example.freelanci.gestionUser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService; // Pour récupérer l'utilisateur par ID

    // Créer un nouveau job
    public Job createJob(Job job) {
        if (job.getClient() != null) {
            // On associe l'utilisateur au job via le client (ID de l'utilisateur)
            job.setClient(job.getClient());  // Associe le client au job
            return jobRepository.save(job);  // Sauvegarde du job avec l'utilisateur lié
        }
        return null;  // Retourner null si le client n'est pas trouvé
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
    public Job updateJob(Long jobId, Job jobDetails) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            // Mise à jour des informations du job avec les nouvelles données
            job.setTitle(jobDetails.getTitle());
            job.setDescription(jobDetails.getDescription());
            job.setBudget(jobDetails.getBudget());
            job.setCategory(jobDetails.getCategory());
            job.setCreatedAt(jobDetails.getCreatedAt());
            job.setDeadline(jobDetails.getDeadline());
            job.setClient(jobDetails.getClient());  // Met à jour l'utilisateur lié
            job.setProjectStatus(jobDetails.getProjectStatus());
            return jobRepository.save(job);  // Sauvegarder les modifications
        }
        return null;  // Si le job n'existe pas, retourner null
    }

    // Supprimer un job par son ID
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);  // Supprimer le job en fonction de l'ID
    }
}
