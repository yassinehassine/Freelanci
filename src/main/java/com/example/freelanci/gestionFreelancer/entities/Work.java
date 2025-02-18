package com.example.freelanci.gestionFreelancer.entities;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity

public class Work {
    @Id
    private long idWork;
    private String Title;
    private String Description;
    private String Image;
    private int Views;
    private Date Date;
    private String Type;
}
