package com.yaksh.review.repo;

import com.yaksh.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> getByCompanyId(Long companyId);
}
