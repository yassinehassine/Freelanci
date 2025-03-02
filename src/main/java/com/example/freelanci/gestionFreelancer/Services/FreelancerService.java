package com.example.freelanci.gestionFreelancer.Services;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import com.example.freelanci.gestionFreelancer.repositories.FreelancerRepository;
import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.freelanci.gestionUser.entities.User.UserRole.FREELANCER;

@Service
@AllArgsConstructor
public class FreelancerService implements IFreelancerService {
    @Autowired
    UserRepository frep;

    @Override
    public List<FreelancerDto> getAllFreelancers() {
        List<User> freeLancers = frep.findAll();
        return freeLancers.stream().filter(fr -> fr.getRole().equals(FREELANCER)).map(fr -> {
            long acceptedCount = fr.getProposals().stream()
                    .filter(p -> "accepted".equalsIgnoreCase(p.getStatus()))
                    .count();
            double pourcentage = 0;
            if (!fr.getProposals().isEmpty()) {
                pourcentage = ((double) acceptedCount / fr.getProposals().size()) * 100;
            }
            return new FreelancerDto(fr.getId(),fr.getEmail(),fr.getProfileDescription(),pourcentage);})
                .collect(Collectors.toList());

    }

}
