package dev.bhardwaj.food_order.dto;

import java.sql.Timestamp;

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
public class NewReviewDto {
	@NotNull(message = "customer id cannot be null")
	private long customerId;
	
	@NotNull(message = "dish id cannot be null")
	private int dishId;
	
	@NotBlank(message = "review comment cannot be blank")
	private String review;
	
	private Timestamp timestamp;
}
