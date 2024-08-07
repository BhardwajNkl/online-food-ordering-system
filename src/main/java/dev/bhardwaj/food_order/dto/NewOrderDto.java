package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	
	@NotBlank(message = "delivery address canno be blank")
	private String deliveryAddress;
	
	@NotNull(message = "list of dish ids must be provided")
	private List<Integer> dishIds;
	
	@NotNull(message = "id of customer must be provided")
	private long customerId;
}
