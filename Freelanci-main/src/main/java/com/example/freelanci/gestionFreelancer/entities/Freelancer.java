package com.example.freelanci.gestionFreelancer.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFreelancer;
    private String Skills;
    private String Domain;
    private int Views;
    private String documentPath;
    @OneToMany(mappedBy="freelancer")
    private Set<Proposal> Proposals;
    @OneToMany(mappedBy="freelancer")
    private Set<Work> Works;

    public long getIdFreelancer() {
        return idFreelancer;
    }

    public String getSkills() {
        return Skills;
    }

    public String getDomain() {
        return Domain;
    }

    public int getViews() {
        return Views;
    }

    public void setIdFreelancer(long idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public void setViews(int views) {
        Views = views;
    }

    public Set<Proposal> getProposals() {
        return Proposals;
    }

    public void setProposals(Set<Proposal> proposals) {
        Proposals = proposals;
    }

    public Set<Work> getWorks() {
        return Works;
    }

    public void setWorks(Set<Work> works) {
        Works = works;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
