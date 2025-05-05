package com.example.freelanci.gestionFreelancer.dto;

import java.io.Serializable;

public class FreelancerDto implements Serializable {

    private long idFreelancer;
    private String email;
    private String description;
    private int Views;
    private String documentPath;
    private double pourcentageAccepted;
    private String skills;
    private String domain;


    public FreelancerDto(long idFreelancer, String skills, String domain, double pourcentageAccepted) {
        this.idFreelancer = idFreelancer;
        this.skills = skills;
        this.domain = domain;
        this.pourcentageAccepted = pourcentageAccepted;
    }
    public FreelancerDto(long idFreelancer, String skills, String domain, int views, String documentPath) {
        this.idFreelancer = idFreelancer;
        this.skills = skills;
        this.domain = domain;
        this.Views = views;
        this.documentPath = documentPath;

    }

    public long getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(long idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
