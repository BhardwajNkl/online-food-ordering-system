package dev.bhardwaj.food_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CreateRestaurantDto;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.service.RestaurantService;

@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	private RestaurantService restaurantService;
	@Override
	public void run(String... args) throws Exception {
//		CreateRestaurantDto restaurantDto = new CreateRestaurantDto("my restra", true);
//		Restaurant restaurant = restaurantService.createRestaurant(restaurantDto);
//		System.out.println("created restra id: "+restaurant.getId());
	}

}
