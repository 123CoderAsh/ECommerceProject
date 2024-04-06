package com.codewithashish.request;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codewithashish.entity.Size;

import lombok.Data;

@Datapublic class CreateProductRequest {

	private String title;

	private String description;

	private int price;

	private int discountPrice;
	
	private int discountedPercent;

	private int quantity;

	private String brand;

	private String color;

	 private Set<Size> size=new HashSet<>();

	private String imageUrl;

	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
}
