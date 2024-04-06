package com.codewithashish.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithashish.entity.Product;
import com.codewithashish.entity.Review;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.repository.ProductRepository;
import com.codewithashish.repository.ReviewRepository;
import com.codewithashish.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Review> getAllReview(Long productId) {
		return reviewRepository.getAllProductReview(productId);
	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());

		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());

		productRepository.save(product);

		return reviewRepository.save(review);
	}

}
