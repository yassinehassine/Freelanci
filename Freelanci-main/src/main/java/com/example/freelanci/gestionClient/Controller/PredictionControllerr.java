package com.example.freelanci.gestionClient.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionControllerr {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody Object inputData) {
        String flaskUrl = "http://127.0.0.1:5000/predict"; // Flask API endpoint

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(inputData, headers);

        // Forward the request to the Flask API
        ResponseEntity<Map> response = restTemplate.exchange(
                flaskUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // Return the Flask API response to the client
        return ResponseEntity.ok(response.getBody());
    }
}
