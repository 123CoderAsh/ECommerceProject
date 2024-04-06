package com.codewithashish.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codewithashish.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p " + "WHERE (:category IS NULL OR p.category.name = :category) "
			+ "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "
			+ "AND (:minDiscounted IS NULL OR p.discountedPercent >= :minDiscount) " + // Corrected parameter name
			"ORDER BY " + // Added missing space
			"CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " + // Added missing space and corrected
																				// parameter name
			"CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC") // Added missing space
	List<Product> filterProducts(
			@Param("category") String category, 
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice, 
			@Param("minDiscount") Integer minDiscount, 
			@Param("sort") String sort);

}
