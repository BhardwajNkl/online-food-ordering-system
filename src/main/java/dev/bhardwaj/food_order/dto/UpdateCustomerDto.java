package dev.bhardwaj.food_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateCustomerDto {
	private long id;
	private String name;
	private String email;
	private String locality;
	private String city;
	private String state;
	private String pinCode;
}
