package com.codewithashish.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithashish.entity.Product;
import com.codewithashish.entity.Rating;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.repository.RatingRepository;
import com.codewithashish.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long prductId) {
		return ratingRepository.getAllProductRating(prductId);
	}

}
