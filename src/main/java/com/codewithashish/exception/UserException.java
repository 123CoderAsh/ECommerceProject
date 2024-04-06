package com.codewithashish.exception;

import org.springframework.data.domain.Example;

public class UserException extends Exception {

	public UserException(String message) {
		super(message);
	}

}
