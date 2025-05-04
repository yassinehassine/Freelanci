package com.example.freelanci.gestionClient.dto;

public class ReviewDTO {
    private Long reviewId;      // for update (optional if you want the ID in the DTO)
    private float rating;
    private String comment;
    private Long jobId;         // which Job this review belongs to

    // Constructors
    public ReviewDTO() {}

    public ReviewDTO(Long reviewId, float rating, String comment, Long jobId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.jobId = jobId;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getJobId() {
        return jobId;
    }
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
