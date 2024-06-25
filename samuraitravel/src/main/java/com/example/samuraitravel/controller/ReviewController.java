package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;

@Controller
@RequestMapping("/review")
public class ReviewController {
	private final ReviewRepository reviewRepository;  
	private final HouseRepository houseRepository;

    public ReviewController(ReviewRepository reviewRepository, HouseRepository houseRepository) {
        this.reviewRepository = reviewRepository;
        this.houseRepository = houseRepository;
       
    }	
    
    @GetMapping("/{houseId}")
    public String index(@PathVariable(name = "houseId") Integer houseId, Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
    	House house = houseRepository.getReferenceById(houseId);
        Page<Review> reviewPage = reviewRepository.findAll(pageable);       
        
        model.addAttribute("reviewPage", reviewPage );             
        model.addAttribute("house", house);
        
        return "review/index";
    } 
    
    @GetMapping("/register")
    public String register(Model model) {
    	
        model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
        
        return "review/register";
    }   
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Review review = reviewRepository.getReferenceById(id);
		
        
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getRating(), review.getComment());
        
        model.addAttribute("reviewEditForm", reviewEditForm);
        
        return "/review/edit";
    }    
}