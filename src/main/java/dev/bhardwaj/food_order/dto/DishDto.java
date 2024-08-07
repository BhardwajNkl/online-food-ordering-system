package dev.bhardwaj.food_order.dto;

import dev.bhardwaj.food_order.entity.Cuisine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishDto {
	private int id;
	private String name;
	private String description;
	private float price;
	private float averageRating;
	private boolean available;
	private Cuisine cuisine;
}
