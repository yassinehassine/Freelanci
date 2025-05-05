package com.example.freelanci.gestionFreelancer.repositories;

import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,Long> {
    @Query("SELECT f FROM Freelancer f ORDER BY RAND()")
    Freelancer findRandomFreelancer(); // Returns a random freelancer
    @Query("SELECT f FROM Freelancer f WHERE f.idFreelancer IN :ids")
    List<Freelancer> findByCustomIdIn(@Param("ids") List<Long> ids);
    @Query("SELECT f FROM Freelancer f WHERE f.idFreelancer IN :ids")
    List<Freelancer> findByIds(@Param("ids") List<Long> ids);


}
