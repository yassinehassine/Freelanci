package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.services.JobSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
public class JobSimulationController {

    @Autowired
    private JobSimulationService jobSimulationService;

    // POST request to simulate job success for a given freelancer
    @GetMapping("/{jobId}/{freelancerId}")
    public double simulateJobSuccess(@PathVariable long jobId, @PathVariable long freelancerId) {
        // Call the service to simulate job success for the specific freelancer
        return jobSimulationService.simulateJobSuccess(jobId, freelancerId);
    }
}
