package com.example.freelanci.gestionUser.services;

import com.example.freelanci.gestionUser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatisticsService {

    @Autowired
    private UserRepository userRepository;

    public UserStatisticsService(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    public Map<String, Long> getUsersByLocation() {
        List<Object[]> results = userRepository.countUsersByLocation();
        Map<String, Long> stats = new HashMap<>();

        for (Object[] row : results) {
            String location = (String) row[0];
            Long count = (Long) row[1];
            stats.put(location, count);
        }

        return stats;
    }
}
