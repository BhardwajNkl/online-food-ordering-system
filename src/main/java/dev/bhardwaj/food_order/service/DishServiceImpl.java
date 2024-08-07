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
import dev.bhardwaj.food_order.entity.Cuisine;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.repository.DishRepository;

@Service
public class DishServiceImpl implements DishService {
	
	private final DishRepository dishRepository;
	
	@Autowired
	private DishConverter dishConverter;
		
	public DishServiceImpl(DishRepository dishRepository) {
		this.dishRepository = dishRepository;
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
		
		List<Dish> dishes = dishRepository.findByCuisine(Cuisine.valueOf(cuisine));
		return dishes.stream()
				.map(dish -> {
					return dishConverter.toDto(dish, DishDto.class);
				}).collect(Collectors.toList());

	}

	@Override
	public List<DishDto> getDishesFromRestaurantByCuisine(int restaurantId, String cuisine) {
		
		List<Dish> dishes = dishRepository.findByRestaurantIdAndCuisine(restaurantId, Cuisine.valueOf(cuisine).ordinal());
		
		return dishes.stream()
				.map(dish -> {
					return dishConverter.toDto(dish, DishDto.class);
				}).collect(Collectors.toList());

	}
	
	@Override
	public List<DishDto> getDishesBasedOnRating() {
		
		List<Dish> dishes = dishRepository.findAllByOrderByAverageRatingDesc();
		
		return dishes.stream()
				.map(dish -> {
					return dishConverter.toDto(dish, DishDto.class);
				}).collect(Collectors.toList());
		
	}
}
