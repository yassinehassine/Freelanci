package com.example.freelanci.gestionFreelancer.Services;

import com.example.freelanci.gestionFreelancer.entities.Proposal;
import com.example.freelanci.gestionFreelancer.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private ProposalRepository proposalRepository;

    public Instances loadDataFromDatabase() {
        // Créer les attributs
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("duration"));
        attributes.add(new Attribute("price"));
        attributes.add(new Attribute("status", List.of("accepted", "refused","pending")));

        // Créer l'ensemble de données
        Instances dataset = new Instances("ProposalData", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Récupérer les données de la base de données
        List<Proposal> proposals = proposalRepository.findAll();
        for (Proposal proposal : proposals) {
            DenseInstance instance = new DenseInstance(3);
            instance.setValue(attributes.get(0), proposal.getDuration());
            instance.setValue(attributes.get(1), proposal.getPrice());
            instance.setValue(attributes.get(2), proposal.getStatus());
            dataset.add(instance);
        }

        return dataset;
    }
}