package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;

import dev.bhardwaj.food_order.entity.Order.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
	private long id;
	private LocalDateTime date;
	private float totalPrice;
	private String deliveryAddress;
	private DeliveryStatus deliveryStatus;
}
