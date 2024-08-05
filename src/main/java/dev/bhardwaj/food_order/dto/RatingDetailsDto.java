package dev.bhardwaj.food_order.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingDetailsDto {
	private long id;
	private long customerId;
	private int dishId;
	private byte rating; // 1 to 5
	private Timestamp timeStamp;
	
	private CustomerDto customerDto;
	private DishDto dishDto;
}
