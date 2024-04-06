package com.codewithashish.service;

import com.codewithashish.entity.Cart;
import com.codewithashish.entity.User;
import com.codewithashish.exception.ProductException;
import com.codewithashish.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);

}
