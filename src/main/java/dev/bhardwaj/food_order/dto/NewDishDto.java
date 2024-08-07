package dev.bhardwaj.food_order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewDishDto {
	@NotBlank(message = "dish must have a name")
	private String name;
	
	private String description;
	
	@NotNull(message = "price cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "dish price must be greater than zero")
	private float price;
	
	private boolean available;
	
	private String cuisine;
	
	@NotNull(message = "restaurantId cannot be null")
	private int restaurantId;
}
