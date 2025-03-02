package com.example.freelanci.gestionFreelancer.Services;
import com.example.freelanci.gestionFreelancer.entities.Proposal;

import java.util.List;
import java.util.Optional;

public interface IProposalService {
    public List<Proposal> getAllProposals();
    public Proposal addProposal(Proposal proposal);
    public Proposal updateProposal(long id, Proposal proposal);
    public void deleteProposal(long idProposal);
    public Proposal getProposal(long idProposal);
    public Optional<Proposal> getBestProposal();

    String predict(long proposalId) throws Exception;
}
