package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionFreelancer.Services.FreelancerService;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class JobSimulationService {

    @Autowired
    private JobService jobService;  // Service to fetch job details

    @Autowired
    private FreelancerService freelancerService;  // Service to get freelancers

    private static final int SIMULATION_RUNS = 1000;

    // Simulate Job Proposal Success based on job budget, freelancer views, and skills match
    public double simulateJobSuccess(long jobId, long freelancerId) {
        Random rand = new Random();
        int successfulProposals = 0;

        // Fetch the job by ID and unwrap Optional
        Optional<Job> jobOptional = jobService.getJobById(jobId);
        if (jobOptional.isEmpty()) {
            return 0.0;  // If job does not exist, return 0% success rate.
        }

        Job job = jobOptional.get();  // Unwrap the Optional to get the Job object

        // Fetch the specific freelancer by ID
        Freelancer freelancer = freelancerService.getFreelancerById(freelancerId);
        if (freelancer == null) {
            return 0.0;  // If freelancer does not exist, return 0% success rate.
        }

        for (int i = 0; i < SIMULATION_RUNS; i++) {
            // Simulate freelancer views (1 to 100)
            double views = freelancer.getViews();

            // Simulate proposal quality based on views (more views -> higher quality)
            double proposalQuality = views * 0.2;

            // Simulate if freelancer's skills match the job category
            boolean isSkillMatch = freelancer.getDomain().equalsIgnoreCase(job.getCategory());

            // Simulate proposal acceptance based on views and skills match
            if (isProposalSuccessful(job, freelancer, proposalQuality, isSkillMatch)) {
                successfulProposals++;
            }
        }

        // Calculate and return the success rate as a percentage
        return ((double) successfulProposals / SIMULATION_RUNS) * 100;
    }

    // Determine if the proposal is successful based on budget, freelancer views, and skill match
    private boolean isProposalSuccessful(Job job, Freelancer freelancer, double proposalQuality, boolean isSkillMatch) {
        // Budget: Lower threshold, but still some randomness in success
        double budgetSuccessChance = Math.min(1.0, job.getBudget() / 1000.0); // Higher budget gives more success chance

        // Views: More views = higher success chance
        double viewSuccessChance = Math.min(1.0, (double) freelancer.getViews() / 100.0);  // Normalized to 100 views

        // Proposal Quality: If the proposal quality is close to 70, it gets closer to success
        double qualitySuccessChance = Math.min(1.0, proposalQuality / 100.0);  // Normalize proposal quality to 100%

        // Skill Match: If skills match, it's a strong contributor
        double skillMatchSuccessChance = isSkillMatch ? 0.8 : 0.4; // Skill match boosts chances

        // Calculate total success chance by weighing each factor
        double totalSuccessChance = (0.4 * budgetSuccessChance) + (0.3 * viewSuccessChance) + (0.2 * qualitySuccessChance) + (0.1 * skillMatchSuccessChance);

        // Randomize the final proposal success chance
        Random rand = new Random();
        double randomFactor = rand.nextDouble(); // Generate a random value between 0 and 1

        // If the random factor is less than the calculated success chance, the proposal is successful
        return randomFactor < totalSuccessChance;
    }
}
