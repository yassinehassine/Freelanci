package com.example.freelanci.gestionClient.repositories;


import com.example.freelanci.gestionClient.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<com.example.freelanci.gestionClient.entities.Event, Long> {
}
