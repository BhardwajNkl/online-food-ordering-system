package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.DishDetailsDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewDishDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.dto.converter.DishConverter;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Cuisine;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Service
public class DishServiceImpl implements DishService {
	
	private final DishRepository dishRepository;
	private final RestaurantRepository restaurantRepository;
	
	@Autowired
	private DishConverter dishConverter;
		
	public DishServiceImpl(DishRepository dishRepository,
			RestaurantRepository restaurantRepository) {
		this.dishRepository = dishRepository;
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	public DishDto createDish(NewDishDto dishDto) {
		Dish dish = dishConverter.toEntity(dishDto);
		Dish createdDish = dishRepository.save(dish);
		return dishConverter.toDto(createdDish, DishDto.class);
	}

	@Override
	public DishDto updateDish(UpdateDishDto dishDto) {
		Dish updatedDish = dishConverter.toEntity(dishDto);
		Dish savedUpdatedDish = dishRepository.save(updatedDish);
		return dishConverter.toDto(savedUpdatedDish, DishDto.class);
	}

	@Override
	public void deleteDish(int dishId) {
		dishRepository.deleteById(dishId);
	}

	@Override
	public DishDetailsDto getDishDetails(int dishId) {
		Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DoesNotExistException("Dish with given id does not exist!"));
		return dishConverter.toDto(dish, DishDetailsDto.class);
	}

	@Override
	public List<DishDto> getDishesByCuisine(String cuisine) {
		List<DishDto> dishes = dishRepository.findAll().stream().filter(dish -> {
			if (dish.getCuisine().equals(Cuisine.valueOf(cuisine)))
				return true;
			return false;
		}).map(dish -> {
			return dishConverter.toDto(dish, DishDto.class);
		}).collect(Collectors.toList());

		return dishes;

	}

	@Override
	public List<DishDto> getDishesFromRestaurantByCuisine(int restaurantId, String cuisine) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RuntimeException("Restaurant does not exist!"));

		return restaurant.getDishesOffered().stream().filter(dish -> {
			if (dish.getCuisine().equals(Cuisine.valueOf(cuisine)))
				return true;
			return false;
		}).map(dish -> {
			return dishConverter.toDto(dish, DishDto.class);
		}).collect(Collectors.toList());

	}
	
	@Override
	public List<DishDto> getDishesBasedOnRating() {
		return dishRepository.findAll()
				.stream()
				.sorted((dish1, dish2)->{
					if(dish1.getAverageRating()>dish2.getAverageRating()) return -1;
					else if(dish1.getAverageRating()==dish2.getAverageRating()) return 0;
					else return 1;
				})
				.map(dish->dishConverter.toDto(dish, DishDto.class))
				.collect(Collectors.toList());
	}
}
