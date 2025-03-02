package com.example.freelanci.gestionFreelancer.entities;
import com.example.freelanci.gestionUser.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;
import java.io.Serializable;
import java.util.Set;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity

public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idWork;
    private String Title;
    private String Description;
    private String Image;
    private int Views;
    private Date Date;
    private String type;
    @ManyToOne
    private User freelancer;

    public long getIdWork() {
        return idWork;
    }

    public void setIdWork(long idWork) {
        this.idWork = idWork;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public User getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(User freelancer) {
        this.freelancer = freelancer;
    }
}
