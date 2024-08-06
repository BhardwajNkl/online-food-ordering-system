package dev.bhardwaj.food_order.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewRatingDto {
	@NotNull(message = "customer id cannot be null")
	private long customerId;
	
	@NotNull(message = "dish id cannot be null")
	private int dishId;
	
	@NotNull(message = "rating cannot be null")
	@Min(value = 1, message = "rating must be in the inclusive integer range 1-5")
	@Max(value = 5, message = "rating must be in the inclusive integer range 1-5")
	private byte rating;
	
	private Timestamp timeStamp;	
}
