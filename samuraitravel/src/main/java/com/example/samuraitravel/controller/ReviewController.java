package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses/{houseId}/review")
public class ReviewController {
	private final ReviewRepository reviewRepository;  
	private final HouseRepository houseRepository;
	private final ReviewService reviewService;

    public ReviewController(ReviewRepository reviewRepository, HouseRepository houseRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.houseRepository = houseRepository;
        this.reviewService = reviewService;
       
    }	
    //レビュー一覧
    @GetMapping
    public String index(@PathVariable("houseId") Integer houseId, Model model, 
    					@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
    	House house = houseRepository.getReferenceById(houseId);
    	Page<Review> reviewPage = reviewRepository.findByHouseOrderByCreatedAtDesc(house, pageable);       
        
        model.addAttribute("reviewPage", reviewPage );             
        model.addAttribute("house", house);
        
        return "review/index";
    } 
    //レビュー登録
    @GetMapping("/register")
    public String register(@PathVariable("houseId") Integer houseId, Model model) {
    	House house = houseRepository.getReferenceById(houseId);
    	
        model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
        model.addAttribute("house", house);
        
        return "review/register";
    }   
    //レビュー登録機能
    @PostMapping("/create")
    public String create(@PathVariable("houseId") Integer houseId,
    					 @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes,
    					 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {        
        if (bindingResult.hasErrors()) {
            return "/review/register";
        }
        
        User user = userDetailsImpl.getUser();
        reviewService.create(user, reviewRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを登録しました。");    
        
        return "redirect:/houses/{housegetId}";
    } 
    //レビュー情報表示
    @GetMapping("/{reviewId}/edit")
    public String edit(@PathVariable("reviewId") Integer reviewId,@PathVariable("houseId") Integer houseId, Model model) {
        Review review = reviewRepository.getReferenceById(reviewId);
        House house = houseRepository.getReferenceById(houseId);
        
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(),review.getRating(), review.getComment());
        
        model.addAttribute("reviewEditForm", reviewEditForm);
        model.addAttribute("house", house);
        model.addAttribute("review", review);
        
        return "/review/edit";
    }  
    //レビュー更新
    @PostMapping("/{reviewid}/update")
    public String update(@PathVariable("reviewid") Integer reviewId,
    					 @ModelAttribute @Validated ReviewEditForm reviewEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
        if (bindingResult.hasErrors()) {
            return "admin/houses/edit";
        }
        
        reviewService.update(reviewEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");
        
        return "redirect:/admin/houses";
    }    
    //レビュー削除
    @PostMapping("/{reviewId}/delete")
    public String delete(@PathVariable("reviewId") Integer reviewId, RedirectAttributes redirectAttributes, Model model) {        
    	reviewRepository.deleteById(reviewId);
    	
        redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
        
        return "redirect:/admin/houses";
    } 
}