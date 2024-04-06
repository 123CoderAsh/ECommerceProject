package com.codewithashish.service;

import java.util.List;

import com.codewithashish.entity.Rating;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req, User user) throws ProductException;
	
	public List<Rating> getProductRating(Long prductId);

}
