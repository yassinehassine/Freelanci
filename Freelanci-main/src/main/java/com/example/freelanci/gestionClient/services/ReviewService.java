package com.example.freelanci.gestionClient.services;

import com.example.freelanci.gestionClient.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.freelanci.gestionClient.entities.Review;
import com.example.freelanci.gestionClient.entities.Job;
import com.example.freelanci.gestionClient.repositories.ReviewRepository;
import com.example.freelanci.gestionClient.repositories.JobRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JobRepository jobRepository;  // Injecting the Job repository to access jobs by jobId

    // Create a new review
    public Review createReview(ReviewDTO dto) {
        // Build the Review entity
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setTimestamp(new Date()); // or from dto if you want

        // Attach to a Job if jobId is provided
        if (dto.getJobId() != null) {
            Job job = jobRepository.findById(dto.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found with id " + dto.getJobId()));
            review.setJob(job);
        }

        // Save and return
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
    public Review updateReview(Long reviewId, ReviewDTO dto) {
        // Find the existing Review
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + reviewId));

        // Update fields
        existingReview.setRating(dto.getRating());
        existingReview.setComment(dto.getComment());
        // Optionally update the job link, if desired
        if (dto.getJobId() != null) {
            Job job = jobRepository.findById(dto.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found with id " + dto.getJobId()));
            existingReview.setJob(job);
        }

        // Save changes
        return reviewRepository.save(existingReview);
    }

    // Delete a review
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }



}
