package com.codewithashish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.entity.Product;
import com.codewithashish.exception.ProductException;
import com.codewithashish.request.CreateProductRequest;
import com.codewithashish.response.ApiResponse;
import com.codewithashish.service.ProductService;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws ProductException {
		Product createProduct = productService.createProduct(req);
		return new ResponseEntity<Product>(createProduct, HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}/deleted")
	public ResponseEntity<ApiResponse> deletedProduct(@PathVariable Long productId) throws ProductException {
		productService.deleteProduct(productId);

		ApiResponse api = new ApiResponse();
		api.setMessage("Product Deleted Successfully...");
		api.setStatus(true);
		return new ResponseEntity<ApiResponse>(api, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct() {
		List<Product> findAllProducts = productService.findAllProducts();
		return new ResponseEntity<List<Product>>(findAllProducts, HttpStatus.OK);
	}

	@PutMapping("/{productId}/updated")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId)
			throws ProductException {
		Product updateProduct = productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(updateProduct, HttpStatus.CREATED);
	}

}
