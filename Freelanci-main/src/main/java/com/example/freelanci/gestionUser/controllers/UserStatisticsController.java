package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.services.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")  // Autoriser les requêtes depuis le frontend
public class UserStatisticsController {

    @Autowired
    private UserStatisticsService statisticsService;

    public UserStatisticsController(UserStatisticsService _statisticsService) {
        this.statisticsService = _statisticsService;
    }

    @GetMapping("/users-by-location")
    public Map<String, Long> getUsersByLocation() {
        return statisticsService.getUsersByLocation();
    }
}
