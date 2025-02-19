package com.example.freelanci.gestionClient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelanci.gestionClient.entities.Review;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.ReviewRepository;
import com.example.freelanci.gestionClient.repositories.JobRepository;


import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JobRepository jobRepository;  // Injecting the Job repository to access jobs by jobId

    // Create a new review
    public Review createReview(Long jobId, Review review) {
        // Retrieve the Job entity using the jobId
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        // Set the job for the review
        review.setJob(job);

        // Save and return the created review
        return reviewRepository.save(review);
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get a review by ID
    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    // Update a review
    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setRating(reviewDetails.getRating());
            review.setComment(reviewDetails.getComment());
            review.setTimestamp(reviewDetails.getTimestamp());
            return reviewRepository.save(review);
        }
        return null;
    }

    // Delete a review
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public double getAverageRatingForJob(Long jobId) {
        // Retrieve the Job entity
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        // Retrieve all reviews for the given Job
        List<Review> reviews = reviewRepository.findByJob(job);

        if (reviews.isEmpty()) {
            return 0.0;  // No reviews for this job
        }

        // Calculate the sum of all ratings
        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        // Calculate and return the average rating
        return totalRating / reviews.size();
    }
}
