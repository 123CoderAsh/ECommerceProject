package com.codewithashish.service;

import com.codewithashish.entity.User;
import com.codewithashish.exception.UserException;

public interface UserService {

	public User findUserById(Long userId) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;

}
