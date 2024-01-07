package com.artur.shop.review.repository;

import com.artur.shop.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
