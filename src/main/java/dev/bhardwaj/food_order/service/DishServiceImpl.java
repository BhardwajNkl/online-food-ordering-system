package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateDishDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import dev.bhardwaj.food_order.repository.DishRepository;

@Service
public class DishServiceImpl implements DishService {
	
	private final DishRepository dishRepository;
	private final DtoToEntityMapper dtoToEntityMapper;
	public DishServiceImpl(DishRepository dishRepository, DtoToEntityMapper dtoToEntityMapper) {
		this.dishRepository = dishRepository;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}

	@Override
	public Dish createDish(CreateDishDto dishDto) {
		Dish dish = dtoToEntityMapper.dishDtoToEntity(dishDto);
		return dishRepository.save(dish); 
	}

	@Override
	public Dish updateDish(UpdateDishDto dishDto) {
		Dish dish = dtoToEntityMapper.updateDishDtoToEntity(dishDto);
		return dishRepository.save(dish);
	}

	@Override
	public void deleteDish(int dishId) {
		dishRepository.deleteById(dishId);
	}

	@Override
	public Dish getDishDetails(int dishId) {
		return dishRepository.findById(dishId)
				.orElseThrow(()-> new RuntimeException("Dish does not exist!"));
	}

//	GET DISHES BY CUISINE FOR A RESTAURANT HAS BEEN IMPLEMENTED IN THE RESTAURANT SERVICE
	// HERE, THIS METHOD IS NOT FOCUSING ON A PARTICULAR RESTAURANT.
	@Override
	public List<Dish> getDishesByCuisine(String cuisine) {
		return dishRepository.findAll().stream().filter(dish -> {
			if (dish.getCuisine().equals(Cuisine.valueOf(cuisine)))
				return true;
			return false;
		}).collect(Collectors.toList());

	}

	@Override
	public List<Dish> getDishesBasedOnRating() {
		return null;
	}

	@Override
	public List<Rating> getRatings(int dishId) {
		Dish dish = dishRepository.findById(dishId)
				.orElseThrow(()-> new RuntimeException("Dish does not exist!"));
		return dish.getRatings();
	}

	@Override
	public List<Review> getReviews(int dishId) {
		Dish dish = dishRepository.findById(dishId)
				.orElseThrow(()-> new RuntimeException("Dish does not exist!"));
		return dish.getReveiws();
	}

}
