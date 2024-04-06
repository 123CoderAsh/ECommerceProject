package com.codewithashish.service;

import java.util.List;

import com.codewithashish.entity.Review;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);

}
