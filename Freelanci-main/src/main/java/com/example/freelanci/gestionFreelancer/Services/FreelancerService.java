package com.example.freelanci.gestionFreelancer.Services;
import com.example.freelanci.gestionFreelancer.dto.FreelancerDto;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import com.example.freelanci.gestionFreelancer.repositories.FreelancerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FreelancerService implements IFreelancerService {
    @Autowired
    FreelancerRepository frep;

    @Override
    public List<FreelancerDto> getAllFreelancers() {
        List<Freelancer> freeLancers = frep.findAll();
        return freeLancers.stream().map(fr -> {
            long acceptedCount = fr.getProposals().stream()
                    .filter(p -> "accepted".equalsIgnoreCase(p.getStatus()))
                    .count();
            double pourcentage = 0;
            if (!fr.getProposals().isEmpty()) {
                pourcentage = ((double) acceptedCount / fr.getProposals().size()) * 100;
            }
            return new FreelancerDto(fr.getIdFreelancer(),fr.getSkills(),fr.getDomain(),fr.getViews(),pourcentage);})
                .collect(Collectors.toList());

    }

    @Override
    public Freelancer addFreelancer(Freelancer freelancer) {
        return frep.save(freelancer);
    }

    @Override
    public Freelancer updateFreelancer(long idFreelancer,Freelancer freelancer) {
        Freelancer fr = frep.findById(idFreelancer).get();
        fr.setSkills(freelancer.getSkills());
        fr.setDomain(freelancer.getDomain());
        return frep.save(fr);
    }

    @Override
    public void deleteFreelancer(long idFreelancer) {
        frep.deleteById(idFreelancer);
    }
    @Override
    public Freelancer getFreelancer(long idFreelancer) {

        return frep.findById(idFreelancer).get();
    }
    @Autowired
    private StockTeleversement fileStorageService;
    // Méthode pour ajouter un document à un freelancer
    public Freelancer addDocumentToFreelancer(Long IdFreelancer, MultipartFile file) throws IOException {
        Freelancer freelancer = frep.findById(IdFreelancer)
                .orElseThrow(() -> new RuntimeException("Freelancer non trouvé"));

        // Stocker le fichier et obtenir le chemin
        String filePath = fileStorageService.storeFile(file);

        // Mettre à jour le chemin du document dans l'entité Freelancer
        freelancer.setDocumentPath(filePath);
        return frep.save(freelancer);
    }
}
