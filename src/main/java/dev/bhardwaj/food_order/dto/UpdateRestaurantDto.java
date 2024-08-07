package dev.bhardwaj.food_order.dto;

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
public class UpdateRestaurantDto {
	@NotNull(message = "id is needed to update the restaurant details")
	private int id;
	
	@NotBlank(message = "restaurant name cannot be blank")
	private String name;
	
	private boolean open;
}
