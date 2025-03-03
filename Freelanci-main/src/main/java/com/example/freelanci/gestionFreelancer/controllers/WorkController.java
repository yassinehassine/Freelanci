package com.example.freelanci.gestionFreelancer.controllers;

import com.example.freelanci.gestionFreelancer.Services.IWorkService;
import com.example.freelanci.gestionFreelancer.Services.WorkService;
import lombok.AllArgsConstructor;
import com.example.freelanci.gestionFreelancer.entities.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/api/works")
public class WorkController {
    @Autowired
    IWorkService workservice;
    @GetMapping("/getWorks")
    public List<Work> allWorks(){
         var all =  workservice.getAllWorks();
         return all;

    }
    @PostMapping("/addWork")
    public Work addWork(@RequestBody Work work){
        return workservice.addWork(work);

    }
    @DeleteMapping("deleteWork/{idW}")
    public void deleteWork(@PathVariable long idW){
        workservice.deleteWork(idW);
    }
    @PutMapping("updateWork/{id}")
    public Work updateWork(@PathVariable long id, @RequestBody Work work) {
        return workservice.updateWork(id, work);
    }

    @Autowired
    private WorkService workService;

    // Endpoint pour récupérer les travaux par type
    @GetMapping("/filter")
    public List<Work> getWorksByType(@RequestParam String type) {
        return workService.getWorksByType(type);
    }
    // Endpoint pour accéder à un Work et incrémenter les vues
    @GetMapping("/{idWork}")
    public ResponseEntity<Work> getWork(@PathVariable Long idWork) {
        Work work = workService.getWorkAndIncrementViews(idWork);
        return ResponseEntity.ok(work);
    }

}
