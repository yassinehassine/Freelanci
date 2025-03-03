package com.example.freelanci.gestionFreelancer.dto;

import java.io.Serializable;

public class FreelancerDto implements Serializable {

    private long idFreelancer;
    private String Skills;
    private String Domain;
    private int Views;
    private String documentPath;
    private double pourcentageAccepted;

    public FreelancerDto(long idFreelancer, String skills, String domain, int views, double pourcentageAccepted) {
        this.idFreelancer = idFreelancer;
        Skills = skills;
        Domain = domain;
        Views = views;
        this.pourcentageAccepted = pourcentageAccepted;
    }

    public long getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(long idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public double getPourcentageAccepted() {
        return pourcentageAccepted;
    }

    public void setPourcentageAccepted(double pourcentageAccepted) {
        this.pourcentageAccepted = pourcentageAccepted;
    }
}
