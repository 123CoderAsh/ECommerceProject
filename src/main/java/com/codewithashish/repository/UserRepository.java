package com.codewithashish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithashish.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

}
