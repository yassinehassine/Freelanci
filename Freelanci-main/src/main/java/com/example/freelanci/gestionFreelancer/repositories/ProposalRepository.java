package com.example.freelanci.gestionFreelancer.repositories;

import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import com.example.freelanci.gestionFreelancer.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal,Long> {
    // Méthode pour récupérer la proposition avec le prix le plus élevé
    @Query("SELECT p FROM Proposal p ORDER BY p.Price ASC LIMIT 1")
    Optional<Proposal> findTopByOrderByProposedAmountAsc();
    // Méthode pour récupérer les propositions d'un freelancer
    List<Proposal> findByFreelancer(Freelancer freelancer); //No property 'id' found for type 'Freelancer'
}




