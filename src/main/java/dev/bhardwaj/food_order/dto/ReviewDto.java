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
public class ReviewDto {
	private long id;
	private long customerId;
	private int dishId;
	private String review;
	private Timestamp timestamp;
}
