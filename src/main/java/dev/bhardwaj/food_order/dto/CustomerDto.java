package dev.bhardwaj.food_order.dto;

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
public class CustomerDto {
	private long id;
	private String name;
	private String email;
	@Embedded
	private Address address;
}
