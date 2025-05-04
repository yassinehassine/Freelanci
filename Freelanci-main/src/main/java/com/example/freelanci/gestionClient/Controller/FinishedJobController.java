package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.services.JobFinishedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class FinishedJobController {

    @Autowired
    private JobFinishedService jobFinishedService;

    // Changed endpoint mapping to avoid conflict with /jobs/{id}
    @GetMapping("/status/finished")
    public ResponseEntity<List<Job>> getFinishedJobs() {
        List<Job> finishedJobs = jobFinishedService.getFinishedJobs();
        return new ResponseEntity<>(finishedJobs, HttpStatus.OK);
    }
}
