package com.example.freelanci;

import com.example.freelanci.gestionFreelancer.repositories.FreelancerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class FreelanciApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreelanciApplication.class, args);
    }

}
