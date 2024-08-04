package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOrderDto {
	private LocalDateTime date;
	private float totalPrice;
	private String deliveryAddress;
	private String deliveryStatus;
	
	private int dishId;
	
	private long customerId;
}
