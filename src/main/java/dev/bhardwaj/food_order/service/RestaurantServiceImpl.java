package dev.bhardwaj.food_order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.NewRestaurantDto;
import dev.bhardwaj.food_order.dto.RestaurantDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.dto.converter.RestaurantConverter;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantConverter restaurantConverter;
	
	private final RestaurantRepository restaurantRepository;
//	private final DtoToEntityMapper dtoToEntityMapper;
	
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
//		this.dtoToEntityMapper = dtoToEntityMapper;
		
	}

	@Override
	public RestaurantDto createRestaurant(NewRestaurantDto restaurantDto) {	
//		Restaurant restaurant = dtoToEntityMapper.newRestaurantDtoToEntity(restaurantDto);
		Restaurant restaurant = restaurantConverter.toEntity(restaurantDto);
		Restaurant createdRestaurant = restaurantRepository.save(restaurant);
		
		// return dto
		return restaurantConverter.toDto(createdRestaurant, RestaurantDto.class);
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
	public RestaurantDetailsDto getRestaurantDetails(int restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(()-> new RuntimeException("Restaurnat does not exist!"));
		return restaurantConverter.toDto(restaurant, RestaurantDetailsDto.class);
	}
	
	@Override
	public Restaurant getRestaurant(int restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(()-> new RuntimeException("Restaurnat does not exist!"));
		
	}
	
}
