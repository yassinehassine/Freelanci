package com.example.freelanci.gestionFreelancer.repositories;

import com.example.freelanci.gestionFreelancer.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {
    // Méthode pour filtrer les travaux par type
    List<Work> findByType(String type);
}
