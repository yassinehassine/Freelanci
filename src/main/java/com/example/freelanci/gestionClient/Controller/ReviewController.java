package com.example.freelanci.gestionClient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.freelanci.gestionClient.entities.Review;
import com.example.freelanci.gestionClient.services.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Create a new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestParam Long jobId, @RequestBody Review review) {
        // Passing the jobId to the service
        Review createdReview = reviewService.createReview(jobId, review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // Get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long reviewId, @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(reviewId, reviewDetails);
        return updatedReview != null ? new ResponseEntity<>(updatedReview, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/average/{jobId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable("jobId") Long jobId) {
        double averageRating = reviewService.getAverageRatingForJob(jobId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }
}
