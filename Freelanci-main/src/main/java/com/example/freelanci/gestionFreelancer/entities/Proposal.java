package com.example.freelanci.gestionFreelancer.entities;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity

public class Proposal {
    @Id
    private long idProposal;
    private String Note;
    private long Price;
    private long Duration;
}
