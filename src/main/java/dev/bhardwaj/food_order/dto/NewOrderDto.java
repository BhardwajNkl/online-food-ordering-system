package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewOrderDto {
	@NotNull(message = "order date cannot be null")
	private LocalDateTime date;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false, message = "total price must be greater than zero")
	private float totalPrice;
	@NotBlank(message = "delivery address canno be blank")
	private String deliveryAddress;
	
	@NotNull(message = "id of ordered dish must be provided")
	private int dishId;
	
	@NotNull(message = "id of customer must be provided")
	private long customerId;
}
