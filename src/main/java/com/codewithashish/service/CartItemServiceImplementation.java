package com.codewithashish.service;

import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithashish.entity.Cart;
import com.codewithashish.entity.CartItem;
import com.codewithashish.entity.Product;
import com.codewithashish.entity.User;
import com.codewithashish.exception.CartItemException;
import com.codewithashish.exception.UserException;
import com.codewithashish.repository.CartItemRepository;
import com.codewithashish.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserService userService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

		CartItem createCartItem = cartItemRepository.save(cartItem);

		return createCartItem;
	}

	@Override
	public CartItem updatCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

		CartItem item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());

		if (user.getId().equals(user)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}

		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

		CartItem cartItemExist = cartItemRepository.isCartItemExist(cart, product, size, userId);

		return cartItemExist;
	}

	@Override
	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException {

		CartItem cartItem = findCartItemById(CartItemId);

		User user = userService.findUserById(cartItem.getUserId());

		User reqUser = userService.findUserById(userId);

		if (user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(CartItemId);
		} else {
			throw new UserException("You can't remove");
		}

	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {

		Optional<CartItem> findById = cartItemRepository.findById(cartItemId);

		if (findById.isPresent()) {
			return findById.get();
		}
		throw new CartItemException("cart Item Not found with Id..." + cartItemId);

	}

}
