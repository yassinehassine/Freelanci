package com.example.freelanci.gestionFreelancer.Services;
import com.example.freelanci.gestionFreelancer.entities.Freelancer;
import com.example.freelanci.gestionFreelancer.repositories.FreelancerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelanci.gestionFreelancer.repositories.ProposalRepository;
import com.example.freelanci.gestionFreelancer.entities.Proposal;
import java.util.Optional;


import java.util.List;

@Service
@AllArgsConstructor

public class ProposalService implements IProposalService {
    @Autowired
    ProposalRepository wrep;
    @Autowired
    ModelService modelService;

    @Override
    public List<Proposal> getAllProposals() {
        return wrep.findAll();
    }

    @Override
    public Proposal addProposal(Proposal proposal) {
        return wrep.save(proposal);
    }

    @Override
    public Proposal updateProposal(long id,Proposal proposal) {
        return wrep.save(proposal);
    }

    @Override
    public void deleteProposal(long idProposal) {
        wrep.deleteById(idProposal);

    }


    @Override
    public Proposal getProposal(long idProposal) {

        return wrep.findById(idProposal).get();
    }

    public Optional<Proposal> getBestProposal() {
        return wrep.findTopByOrderByProposedAmountAsc();
    }

    @Override
    public String predict(long proposalId) throws Exception {
        Proposal proposal = wrep.findById(proposalId).get();
        return modelService.predict(proposal.getDuration(),proposal.getPrice());
    }
}
