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

import com.codewithashish.entity.Address;
import com.codewithashish.entity.Order;
import com.codewithashish.entity.User;
import com.codewithashish.exception.OrderException;
import com.codewithashish.exception.UserException;
import com.codewithashish.service.OrderService;
import com.codewithashish.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws OrderException, UserException {

		User user = userService.findUserProfileByJwt(jwt);
		Order createOrder = orderService.createOrder(user, shippingAddress);

		System.out.println("Order" + createOrder);

		return new ResponseEntity<Order>(createOrder, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt)
			throws UserException {

		User user = userService.findUserProfileByJwt(jwt);
		List<Order> userOrderHistory = orderService.userOrderHistory(user.getId());

		return new ResponseEntity<>(userOrderHistory, HttpStatus.CREATED);
	}

	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException, OrderException {

		User user = userService.findUserProfileByJwt(jwt);
		Order findOrderById = orderService.findOrderById(orderId);

		return new ResponseEntity<Order>(findOrderById, HttpStatus.OK);

	}

}
