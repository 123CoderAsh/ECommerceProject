package com.codewithashish.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.codewithashish.entity.Category;
import com.codewithashish.entity.Product;
import com.codewithashish.exception.ProductException;
import com.codewithashish.repository.CategoryRepository;
import com.codewithashish.repository.ProductRepository;
import com.codewithashish.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

		if (topLevel == null) {

			Category topLavelCategory = new Category();
			topLavelCategory.setName(req.getTopLevelCategory());
			topLavelCategory.setLevel(1);

			topLevel = categoryRepository.save(topLavelCategory);
		}

		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
		if (secondLevel == null) {

			Category secondLavelCategory = new Category();
			secondLavelCategory.setName(req.getSecondLevelCategory());
			secondLavelCategory.setParentCategory(topLevel);
			secondLavelCategory.setLevel(2);

			secondLevel = categoryRepository.save(secondLavelCategory);
		}

		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
				secondLevel.getName());
		if (thirdLevel == null) {

			Category thirdLavelCategory = new Category();
			thirdLavelCategory.setName(req.getThirdLevelCategory());
			thirdLavelCategory.setParentCategory(secondLevel);
			thirdLavelCategory.setLevel(3);

			thirdLevel = categoryRepository.save(thirdLavelCategory);
		}

		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountPrice());
		product.setDiscountedPercent(req.getDiscountedPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Product saveProduct = productRepository.save(product);

		return saveProduct;

	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		System.out.println("Delete Product " + product.getId() + " - " + productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product Deleted Successfully...";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);

		if (req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}
		if (req.getDescription() != null) {
			product.setDescription(req.getDescription());
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {

		Optional<Product> product = productRepository.findById(productId);

		if (product.isPresent()) {
			return product.get();
		}
		throw new ProductException("Product Not Found with id - " + productId);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
	
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		PageRequest pageble = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}

		if (stock != null) {
			if (stock.equalsIgnoreCase("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equalsIgnoreCase("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}

		int startIndex = (int) pageble.getOffset();
		int endIndex = Math.min(startIndex + pageble.getPageSize(), products.size());

		List<Product> pageContext = products.subList(startIndex, endIndex);

		Page<Product> filteredProducts = new PageImpl<>(pageContext, pageble, products.size());

		return filteredProducts;
	}

	@Override
	public List<Product> findAllProducts() {
		
		return productRepository.findAll();
	}

}
