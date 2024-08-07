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
	private byte rating;
	private Timestamp timeStamp;
	
	private CustomerDto customerDto;
	private DishDto dishDto;
}
