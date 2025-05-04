package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobFilterService {

    @Autowired
    private JobRepository jobRepository;

    /**
     * Returns jobs posted by a specific client filtered by a date range.
     *
     * @param clientId the id of the client
     * @param from the starting date (inclusive)
     * @param to the ending date (inclusive)
     * @return list of jobs
     */
    public List<Job> getJobsByClientAndDate(Long clientId, LocalDate from, LocalDate to) {
        if (from != null && to != null) {
            // Convert LocalDate to LocalDateTime; the end date is exclusive so we add one day.
            LocalDateTime startOfDay = from.atStartOfDay();
            LocalDateTime endOfDay = to.plusDays(1).atStartOfDay();
            return jobRepository.findByClientIdAndCreatedAtBetween(clientId, startOfDay, endOfDay);
        } else {
            // If dates are not provided, return all jobs for the client.
            return jobRepository.findByClientId(clientId);
        }
    }
}
