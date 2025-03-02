package com.example.freelanci.gestionFreelancer.Services;

import com.example.freelanci.gestionFreelancer.repositories.WorkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelanci.gestionFreelancer.repositories.WorkRepository;
import com.example.freelanci.gestionFreelancer.entities.Work;
import java.util.Optional;



import java.util.List;

@Service
@AllArgsConstructor
public class WorkService implements IWorkService {
    @Autowired
    WorkRepository wrep;
    @Override
    public List<Work> getAllWorks() {
        return wrep.findAll();
    }

    @Override
    public Work addWork(Work work) {
        return wrep.save(work);
    }

    @Override
    public Work updateWork(long idWork,Work work) {
        return wrep.save(work);
    }

    @Override
    public void deleteWork(long idWork) {
        wrep.deleteById(idWork);

    }

    @Override
    public Work getWork(long idWork) {

        return wrep.findById(idWork).get();
    }
    @Autowired
    private WorkRepository workRepository;

    // Méthode pour récupérer les travaux par type
    public List<Work> getWorksByType(String type) {
        return workRepository.findByType(type);
    }
    // Méthode pour récupérer un Work par son ID et incrémenter les vues
    public Work getWorkAndIncrementViews(Long idWork) {
        Optional<Work> optionalWork = workRepository.findById(idWork);
        if (optionalWork.isPresent()) {
            Work work = optionalWork.get();
            work.setViews(work.getViews() + 1); // Incrémenter les vues
            return workRepository.save(work); // Sauvegarder les modifications
        } else {
            throw new RuntimeException("Work non trouvé");
        }
    }

}
