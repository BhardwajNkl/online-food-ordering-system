package dev.bhardwaj.food_order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantDetailsDto {
	private int id;
	private String name;
	private boolean open;
	private List<DishDto> dishesOffered;
}
