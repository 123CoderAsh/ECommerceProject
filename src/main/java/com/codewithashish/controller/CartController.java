package com.codewithashish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.entity.Cart;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.exception.UserException;
import com.codewithashish.request.AddItemRequest;
import com.codewithashish.response.ApiResponse;
import com.codewithashish.service.CartService;
import com.codewithashish.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String long1) throws UserException {

		User user = userService.findUserProfileByJwt(long1);
		Cart cart = cartService.findUserCart(user.getId());

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {

		User user = userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), req);

		ApiResponse api = new ApiResponse();
		api.setMessage("item added to cart");
		api.setStatus(true);

		return new ResponseEntity<ApiResponse>(api, HttpStatus.OK);
	}

}
