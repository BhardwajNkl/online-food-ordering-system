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
public class ReviewDetailsDto {
	private long id;
	private String review;
	private Timestamp timeStamp;
	
	private CustomerDto customerDto;
	private DishDto dishDto;
}
