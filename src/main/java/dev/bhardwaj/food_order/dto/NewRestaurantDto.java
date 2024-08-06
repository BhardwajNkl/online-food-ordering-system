package dev.bhardwaj.food_order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewRestaurantDto {
	@NotBlank(message = "restaurant name cannot be blank")
	private String name;
	
	private boolean open;
}
