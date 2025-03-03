package com.example.freelanci.gestionFreelancer.controllers;
import com.example.freelanci.gestionFreelancer.Services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PredictionController {

    @Autowired
    private ModelService modelService;

    @GetMapping("/train")
    public String trainModel() throws Exception {
        modelService.trainModel();
        return "Modèle entraîné avec succès !";
    }

}