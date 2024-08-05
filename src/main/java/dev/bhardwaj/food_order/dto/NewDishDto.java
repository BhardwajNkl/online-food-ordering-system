package dev.bhardwaj.food_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewDishDto {
	private String name;
	private String description;
	private float price;
	private boolean available;
	private String cuisine;
	private int restaurantId;
}
