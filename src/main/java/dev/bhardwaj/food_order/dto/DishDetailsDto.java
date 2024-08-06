package dev.bhardwaj.food_order.dto;

import java.util.List;

import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishDetailsDto {
	private int id;
	private String name;
	private String description;
	private float price;
	private float averageRating;
	private boolean available;
	private Cuisine cuisine;
	
	private RestaurantDto restaurant;
	
//	private List<OrderDetailsDto> orders;
	
//	private List<RatingDetailsDto> ratings;
	
	private List<ReviewDetailsDto> reveiws;
}
