package dev.bhardwaj.food_order.dto;

import java.util.List;

import dev.bhardwaj.food_order.entity.Customer.Address;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDetailsDto {
	private long id;
	private String name;
	private String email;
	@Embedded
	private Address address;
	
	private List<OrderDto> orders;
	
	private List<RatingDetailsDto> ratingsGiven;
	
	private List<ReviewDetailsDto> reviewsGiven;
}
