package com.example.freelanci.gestionFreelancer.Services;

import com.example.freelanci.gestionFreelancer.entities.Work;

import java.util.List;

public interface IWorkService {
    public List<Work> getAllWorks();
    public Work addWork(Work work);
    public Work updateWork(long idWork,Work work);
    public void deleteWork(long idWork);
    public Work getWork(long idWork);
    public List<Work> getWorksByType(String type);
    public Work getWorkAndIncrementViews(Long idWork);
}
