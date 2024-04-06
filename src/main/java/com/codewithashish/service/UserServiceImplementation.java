package com.codewithashish.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithashish.config.JwtProvider;
import com.codewithashish.entity.User;
import com.codewithashish.exception.UserException;
import com.codewithashish.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserById(Long userId) throws UserException {

		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UserException("User Not Found This id..." + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email = jwtProvider.getEmailFromToken(jwt);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User Not Found For this email.." + email);
		}

		return user;
	}

}
