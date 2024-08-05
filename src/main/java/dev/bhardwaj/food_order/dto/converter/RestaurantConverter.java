package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewRestaurantDto;
import dev.bhardwaj.food_order.dto.RestaurantDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.dto.UpdateRestaurantDto;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Component
public class RestaurantConverter {
	
	@Autowired
	private DishConverter dishConverter;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	
	
	public Restaurant toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		Restaurant restaurant = new Restaurant();

		switch (dto.getClass().getSimpleName()) {
		case "NewRestaurantDto":
			NewRestaurantDto newDto = (NewRestaurantDto) dto;
			restaurant.setName(newDto.getName());
			restaurant.setOpen(newDto.isOpen());
			return restaurant;
			
		case "UpdateRestaurantDto":
			UpdateRestaurantDto updateDto = (UpdateRestaurantDto) dto;
			Restaurant restaurantToUpdate = restaurantRepository
					.findById(updateDto.getId())
					.orElseThrow(()->new RuntimeException("Restaurant does not exist!"));
			restaurantToUpdate.setName(updateDto.getName());
			restaurantToUpdate.setOpen(updateDto.isOpen());
			return restaurantToUpdate;
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dto.getClass().getSimpleName());
		}
	}

	public <T> T toDto(Restaurant restaurant, Class<T> dtoClass) {
		if (restaurant == null) {
			return null;
		}

//		T dto;
//
//		try {
//			dto = dtoClass.getDeclaredConstructor().newInstance();
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to create DTO instance", e);
//		}

		switch (dtoClass.getSimpleName()) {
		
		case "RestaurantDto":
			RestaurantDto restaurantDto = new RestaurantDto();
			restaurantDto.setId(restaurant.getId());
			restaurantDto.setName(restaurant.getName());
			restaurantDto.setOpen(restaurant.isOpen());
			return dtoClass.cast(restaurantDto);
			
		case "RestaurantDetailsDto":
			RestaurantDetailsDto restaurantDetailsDto = new RestaurantDetailsDto();
			restaurantDetailsDto.setId(restaurant.getId());
			restaurantDetailsDto.setName(restaurant.getName());
			restaurantDetailsDto.setOpen(restaurant.isOpen());
			
			List<DishDto> dishesOffered = restaurant.getDishesOffered()
					.stream()
					.map(
							dish->{
								return dishConverter.toDto(dish, DishDto.class);
								}
						)
					.collect(Collectors.toList());	
			
			restaurantDetailsDto.setDishesOffered(dishesOffered);
			
			return dtoClass.cast(restaurantDetailsDto);
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dtoClass.getSimpleName());
		}

	}
}
