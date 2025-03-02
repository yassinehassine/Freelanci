package com.example.freelanci.gestionFreelancer.entities;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.Serializable;
import java.util.List;
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


    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
