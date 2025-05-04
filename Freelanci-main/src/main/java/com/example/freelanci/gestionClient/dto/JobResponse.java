package com.example.freelanci.gestionClient.dto;

import lombok.Data;
import java.util.Date;

@Data
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private float budget;
    private String category;
    private Long clientId;
    private Date createdAt;
    private Date deadline;
    private String projectStatus;
}
