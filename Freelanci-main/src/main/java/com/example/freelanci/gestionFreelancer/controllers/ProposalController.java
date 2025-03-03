package com.example.freelanci.gestionFreelancer.controllers;

import com.example.freelanci.gestionFreelancer.Services.IProposalService;
import com.example.freelanci.gestionFreelancer.entities.Proposal;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/api/proposals")
public class ProposalController {
    @Autowired
    IProposalService proposalservice;
    @GetMapping("/getAllProposals")
    public List<Proposal> allProposals(){
        var all = proposalservice.getAllProposals();
        return all;

    }
    @PostMapping("/addProposal")
    public Proposal addProposal(@RequestBody Proposal proposal){
        return proposalservice.addProposal(proposal);

    }
    @DeleteMapping("deleteProposal/{idP}")
    public void deleteProposal(@PathVariable long idP){
        proposalservice.deleteProposal(idP);
    }
    @PutMapping("updateProposal/{id}")
    public Proposal updateProposal(@PathVariable long id, @RequestBody Proposal proposal) {
        return proposalservice.updateProposal(id, proposal);
    }

    // Endpoint pour récupérer la meilleure proposition
    @GetMapping("/best")
    public ResponseEntity<Proposal> getBestProposal() {
        Optional<Proposal> bestProposal = proposalservice.getBestProposal();
        return bestProposal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/predict")
    public String predict(@RequestParam long proposalId) throws Exception {
        return proposalservice.predict(proposalId);
    }

}
