package com.codewithashish.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithashish.entity.Address;
import com.codewithashish.entity.Cart;
import com.codewithashish.entity.CartItem;
import com.codewithashish.entity.Order;
import com.codewithashish.entity.OrderItem;
import com.codewithashish.entity.User;
import com.codewithashish.exception.OrderException;
import com.codewithashish.repository.AddressRepository;
import com.codewithashish.repository.CartItemRepository;
import com.codewithashish.repository.CartRepository;
import com.codewithashish.repository.OrderItemRepository;
import com.codewithashish.repository.OrderRepository;
import com.codewithashish.repository.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddreses().add(address);
		userRepository.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item : cart.getCartItem()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem save = orderItemRepository.save(orderItem);
			
			orderItems.add(save);			
		}
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItem(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalitem(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order order = orderRepository.save(createdOrder);
		
		for(OrderItem item : orderItems) {
			item.setOrder(order);
			orderItemRepository.save(item);
		}
		
		return order;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {

		Optional<Order> order = orderRepository.findById(orderId);

		if (order.isPresent()) {
			return order.get();
		}

		throw new OrderException("Order Not Exist With id" + orderId);
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {

		List<Order> userOrder = orderRepository.getUserOrder(userId);

		return userOrder;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {

		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");

		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {

		Order findOrderById = findOrderById(orderId);
		findOrderById.setOrderStatus("CONFIRMED");
		return orderRepository.save(findOrderById);
	}

	@Override
	public Order shippingOrder(Long orderId) throws OrderException {

		Order findOrderById = findOrderById(orderId);
		findOrderById.setOrderStatus("SHIPPED");

		return orderRepository.save(findOrderById);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {

		Order findOrderById = findOrderById(orderId);
		findOrderById.setOrderStatus("DELIVERED");

		return orderRepository.save(findOrderById);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {

		Order findOrderById = findOrderById(orderId);
		findOrderById.setOrderStatus("CANCELLED");

		return orderRepository.save(findOrderById);
	}

	@Override
	public List<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {

		Order findOrderById = findOrderById(orderId);

		orderRepository.deleteById(orderId);

	}

}
