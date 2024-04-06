package com.codewithashish.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException us, WebRequest web) {

		ErrorDetails err = new ErrorDetails(us.getMessage(), web.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException pe, WebRequest web) {

		ErrorDetails err = new ErrorDetails(pe.getMessage(), web.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CartItemException.class)
	public ResponseEntity<ErrorDetails> cartItemException(CartItemException cart, WebRequest web) {

		ErrorDetails err = new ErrorDetails(cart.getMessage(), web.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetails> getOrderException(OrderException order, WebRequest web) {

		ErrorDetails err = new ErrorDetails(order.getMessage(), web.getDescription(false), LocalDateTime.now());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
