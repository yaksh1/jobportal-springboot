package com.yaksh.review.controller;

import com.yaksh.review.Util.ResponseDataDTO;
import com.yaksh.review.model.Review;
import com.yaksh.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping
    public ResponseEntity<ResponseDataDTO> getAllReviews(@RequestParam Long companyId){
        ResponseDataDTO reviews = reviewService.getAllReviews(companyId);
        if(!reviews.isStatus()){
            return new ResponseEntity(reviews,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseDataDTO saveReview(@RequestParam Long companyId, @RequestBody Review review){
        boolean isSaved =  reviewService.saveReview(companyId,review);
        if(isSaved){
            return new ResponseDataDTO("Review saved.",true);
        }
        return new ResponseDataDTO("Review not saved.Company not found.",false);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ResponseDataDTO> getReviewById(@PathVariable Long reviewId){
        ResponseDataDTO review = reviewService.getReviewById(reviewId);
        if(review.isStatus()){
            return ResponseEntity.ok(review);
        }
        return new ResponseEntity(review,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseDataDTO> updateReview(@PathVariable Long reviewId,@RequestBody Review updatedReview){
        ResponseDataDTO review = reviewService.updateReview(reviewId,updatedReview);
        if(review.isStatus()){
            return ResponseEntity.ok(review);
        }
        return new ResponseEntity<>(review,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ResponseDataDTO> deleteReview( @PathVariable Long reviewId){
        ResponseDataDTO responseDataDTO = reviewService.deleteReview(reviewId);
        if(responseDataDTO.isStatus()){
            return ResponseEntity.ok(responseDataDTO);
        }
        return new ResponseEntity<>(responseDataDTO,HttpStatus.NOT_FOUND);
    }
}
