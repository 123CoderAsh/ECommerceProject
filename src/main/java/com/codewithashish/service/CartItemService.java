package com.codewithashish.service;

import com.codewithashish.entity.Cart;
import com.codewithashish.entity.CartItem;
import com.codewithashish.entity.Product;
import com.codewithashish.exception.CartItemException;
import com.codewithashish.exception.UserException;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);

	public CartItem updatCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException;

	public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
