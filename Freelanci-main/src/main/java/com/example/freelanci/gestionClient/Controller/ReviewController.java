package com.example.freelanci.gestionClient.Controller;

import com.example.freelanci.gestionClient.dto.ReviewDTO;
import com.example.freelanci.gestionClient.repositories.ReviewRepository;
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
    @Autowired
    private ReviewRepository reviewRepository;

    // Create a new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO dto) {
        Review created = reviewService.createReview(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
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
    public ResponseEntity<Review> updateReview(
            @PathVariable("id") Long reviewId,
            @RequestBody ReviewDTO dto
    ) {
        Review updated = reviewService.updateReview(reviewId, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByJobId(@PathVariable Long jobId) {
        List<ReviewDTO> reviews = reviewRepository.findAllByJobId(jobId);
        return ResponseEntity.ok(reviews);
    }
}
