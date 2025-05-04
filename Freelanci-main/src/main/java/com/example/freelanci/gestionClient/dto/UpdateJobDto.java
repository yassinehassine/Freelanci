package com.example.freelanci.gestionClient.dto;

import lombok.Data;

import java.util.Date;

/**
 * DTO used for updating an existing Job.
 */
@Data
public class UpdateJobDto {
    private String title;
    private String description;
    private float budget;
    private String category;

    // The ID of the user who is the client (optional, only if you want to allow changing client)
    private Long clientId;

    private Date createdAt;
    private Date deadline;
    private String projectStatus; // e.g. "NEST_PAS_DEBUTE", "EN_COURS", or "TERMINER"
}
