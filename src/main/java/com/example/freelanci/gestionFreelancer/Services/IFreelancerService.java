package com.example.freelanci.gestionFreelancer.Services;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface IFreelancerService {
    public List<FreelancerDto> getAllFreelancers();
    public Freelancer addFreelancer(Freelancer freelancer);
    public Freelancer updateFreelancer(long id, Freelancer freelancer);
    public void deleteFreelancer(long idFreelancer);
    public Freelancer getFreelancer(long idFreelancer);
    public Freelancer addDocumentToFreelancer(Long IdFreelancer, MultipartFile file) throws IOException;
}
