package com.example.freelanci.gestionFreelancer.controllers;

import com.example.freelanci.gestionFreelancer.Services.FreelancerService;
import com.example.freelanci.gestionFreelancer.Services.IFreelancerService;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

}
