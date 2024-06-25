package com.example.samuraitravel.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
	}    
	
	@Transactional
    public void create(ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		review.setRating(reviewRegisterForm.getRating());                
		review.setComment(reviewRegisterForm.getComment());
		
		reviewRepository.save(review);
	}

}
