package com.yaksh.review.service;


import com.yaksh.review.Util.ResponseDataDTO;
import com.yaksh.review.dto.ReviewResponseDTO;
import com.yaksh.review.model.Review;
import com.yaksh.review.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public ResponseDataDTO getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.getByCompanyId(companyId);
        List<ReviewResponseDTO> reviewResponseDTO= reviews.stream().map(review -> mapToResponse(review)).collect(Collectors.toList());
        return new ResponseDataDTO("Reviews fetched",true,reviewResponseDTO);
    }

    private ReviewResponseDTO mapToResponse(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .title(review.getTitle())
                .description(review.getDescription())
                .rating(review.getRating())
                .build();
    }

    public boolean saveReview(Long companyId,Review review) {
        try{
            if(companyId!=null && review!=null){
                review.setCompanyId(companyId);
                reviewRepository.save(review);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public ResponseDataDTO getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review==null){
            return new ResponseDataDTO("Review does not exist",false);
        }

        return new ResponseDataDTO("Review found",true,review);

    }

    public ResponseDataDTO updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            reviewRepository.save(review);
            return new ResponseDataDTO("Review updated.",true,review);
        }
        return new ResponseDataDTO("Review update failed.",false);
    }

    public ResponseDataDTO deleteReview(Long reviewId) {
        if(!reviewRepository.existsById(reviewId)){
            return new ResponseDataDTO("Review does not exist",false);
        }
        reviewRepository.deleteById(reviewId);
        return new ResponseDataDTO("Review deleted.",true);
    }
}
