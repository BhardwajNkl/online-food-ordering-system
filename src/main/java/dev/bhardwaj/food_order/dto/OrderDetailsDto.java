package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;
import java.util.List;

import dev.bhardwaj.food_order.entity.Order.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailsDto {
	private long id;
	private LocalDateTime date;
	private float totalPrice;
	private String deliveryAddress;
	private DeliveryStatus deliveryStatus;
	
	private List<DishDto> dishes;
	
	private CustomerDto customer;
}
