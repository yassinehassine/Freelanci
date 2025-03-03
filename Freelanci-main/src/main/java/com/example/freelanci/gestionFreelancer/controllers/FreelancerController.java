package com.example.freelanci.gestionFreelancer.controllers;

import com.example.freelanci.gestionFreelancer.Services.IFreelancerService;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/freelancers")

public class FreelancerController {
    @Autowired
    IFreelancerService freelancerservice;

    @GetMapping(value = "/getAllFreelancers")
    public List<FreelancerDto> allFreelancers() {
        return freelancerservice.getAllFreelancers();

    }

    @PostMapping("/addFreelancer")
    public Freelancer addFreelancer(@RequestBody Freelancer freelancer) {
        return freelancerservice.addFreelancer(freelancer);

    }

    @DeleteMapping("deleteFreelancer/{idP}")
    public void deleteFreelancer(@PathVariable long idP) {
        freelancerservice.deleteFreelancer(idP);
    }

    @PutMapping("updateFreelancer/{id}")
    public Freelancer updateFreelancer(@PathVariable long id, @RequestBody Freelancer freelancer) {
        return freelancerservice.updateFreelancer(id, freelancer);
    }
    // Endpoint pour ajouter un document à un freelancer
    @PostMapping("/{freelancerId}/documents")
    public Freelancer addDocument(
            @PathVariable Long freelancerId,
            @RequestParam("file") MultipartFile file) throws IOException {
        return freelancerservice.addDocumentToFreelancer(freelancerId, file);
    }
}
