package com.codewithashish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.codewithashish.entity.Order;
import com.codewithashish.exception.OrderException;
import com.codewithashish.response.ApiResponse;
import com.codewithashish.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {
		List<Order> allOrders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(allOrders, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order confirmedOrder = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(confirmedOrder, HttpStatus.OK);
	}

	@PutMapping("{orderId}/shipped")
	public ResponseEntity<Order> shippedOrderhandler(@PathVariable Long ordeId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order shippingOrder = orderService.shippingOrder(ordeId);
		return new ResponseEntity<Order>(shippingOrder, HttpStatus.OK);
	}

	@PutMapping("{orderId}/delivered")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order deliveredOrder = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(deliveredOrder, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order cancledOrder = orderService.cancledOrder(orderId);
		return new ResponseEntity<Order>(cancledOrder, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/deleted")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		orderService.deleteOrder(orderId);

		ApiResponse api = new ApiResponse();
		api.setMessage("order Deleted success");
		api.setStatus(true);
		
		return new ResponseEntity<>(api, HttpStatus.OK);
	}

}
