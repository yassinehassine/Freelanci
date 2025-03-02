package com.example.freelanci.gestionFreelancer.Services;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    @Autowired
    private DataService dataService;

    private J48 model;

    public void trainModel() throws Exception {
        Instances dataset = dataService.loadDataFromDatabase();
        model = new J48();
        model.buildClassifier(dataset);
        System.out.println("Modèle entraîné avec succès !");
    }

    public String predict(double feature1, double feature2) throws Exception {
        if (model == null) {
            throw new IllegalStateException("Le modèle n'a pas été entraîné.");
        }

        Instances dataset = dataService.loadDataFromDatabase();
        DenseInstance newInstance = new DenseInstance(3);
        newInstance.setDataset(dataset);
        newInstance.setValue(0, feature1);
        newInstance.setValue(1, feature2);

        double prediction = model.classifyInstance(newInstance);
        return dataset.classAttribute().value((int) prediction);
    }
}