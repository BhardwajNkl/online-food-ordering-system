package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateDishDto;
import dev.bhardwaj.food_order.dto.CreateRestaurantDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	private final RestaurantRepository restaurantRepository;
	private final DishService dishService;
	private final OrderService orderService;
	private final DtoToEntityMapper dtoToEntityMapper;
	
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
			DishService dishService,
			OrderService orderService,
			DtoToEntityMapper dtoToEntityMapper) {
		this.restaurantRepository = restaurantRepository;
		this.dishService = dishService;
		this.orderService = orderService;
		this.dtoToEntityMapper = dtoToEntityMapper;
		
	}

	@Override
	public Restaurant createRestaurant(CreateRestaurantDto restaurantDto) {	
		Restaurant restaurant = dtoToEntityMapper.restaurantDtoToEntity(restaurantDto);
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(int restaurantId) {
		restaurantRepository.deleteById(restaurantId);
	}

	@Override
	public void openRestaurant(int restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(()-> new RuntimeException("Restaurnat does not exist!"));
		restaurant.setOpen(true);
		restaurantRepository.save(restaurant);
	}

	@Override
	public void closeRestaurant(int restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(()-> new RuntimeException("Restaurnat does not exist!"));
		restaurant.setOpen(false);
		restaurantRepository.save(restaurant);
	}

	@Override
	public Dish addDish(CreateDishDto dishDto) {
		return dishService.createDish(dishDto);
	}

	@Override
	public void deleteDish(int dishId) {
		dishService.deleteDish(dishId);
	}

	@Override
	public Dish updateDish(UpdateDishDto dishDto) {
		return dishService.updateDish(dishDto);
	}
	
	

	@Override
	public List<Order> getAllOrdersForRestaurant(int restaurantId) {
		
		return orderService.getOrdersForRestaurant(restaurantId);
	}

	@Override
	public List<Dish> getDishesFromRestaurantByCuisine(int restaurantId, String cuisine) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(()->new RuntimeException("Restaurant does not exist!"));
		return restaurant.getDishesOffered()
		.stream()
		.filter(
				dish->{
					if(dish.getCuisine().equals(Cuisine.valueOf(cuisine)))
						return true;
					return false;}
				).collect(Collectors.toList());
	}
	
}
