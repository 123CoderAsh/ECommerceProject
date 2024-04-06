package com.codewithashish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.entity.Review;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.exception.UserException;
import com.codewithashish.request.ReviewRequest;
import com.codewithashish.service.ReviewService;
import com.codewithashish.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

	@PostMapping("/review")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {

		User user = userService.findUserProfileByJwt(jwt);
		Review createReview = reviewService.createReview(req, user);

		return new ResponseEntity<Review>(createReview, HttpStatus.CREATED);
	}

	@GetMapping("/allreviews")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long producId)
			throws UserException, ProductException {

		List<Review> allReview = reviewService.getAllReview(producId);

		return new ResponseEntity<List<Review>>(allReview, HttpStatus.ACCEPTED);
	}

}
