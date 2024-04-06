package com.codewithashish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.entity.Product;
import com.codewithashish.exception.ProductException;
import com.codewithashish.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/allProducts")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
			@RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice,
			@RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
			@RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {

		Page<Product> allProduct = productService.getAllProduct(category, size, color, minPrice, minDiscount, maxPrice,
				sort, stock, pageNumber, pageSize);

		System.out.println("All Completed Product");

		return new ResponseEntity<Page<Product>>(allProduct, HttpStatus.ACCEPTED);
	}

	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {

		Product findProductById = productService.findProductById(productId);

		return new ResponseEntity<Product>(findProductById, HttpStatus.OK);
	}

}
