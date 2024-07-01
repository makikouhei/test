package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;


@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final HouseRepository houseRepository;

    
	public ReviewService(ReviewRepository reviewRepository, HouseRepository houseRepository) {
        this.reviewRepository = reviewRepository;
        this.houseRepository = houseRepository;
          
	}    
	
	@Transactional
    public void create(User user, ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		House house = houseRepository.getReferenceById(reviewRegisterForm.getHouseId());
		review.setRating(reviewRegisterForm.getRating());                
		review.setComment(reviewRegisterForm.getComment());
		review.setHouse(house);
		review.setUser(user);
		
		reviewRepository.save(review);
	}
	
	@Transactional
    public void update(ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
        
        review.setRating(reviewEditForm.getRating());                
        review.setComment(reviewEditForm.getComment());
       
                    
        reviewRepository.save(review);
    }    
	

}
