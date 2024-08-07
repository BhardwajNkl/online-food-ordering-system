package dev.bhardwaj.food_order.dto;

import jakarta.validation.constraints.Email;
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
public class UpdateCustomerDto {
	@NotNull(message = "id is required to update the customer details")
	private long id;
	
	@NotBlank(message = "name cannot be blank")
	private String name;
	
	@Email(message = "email is not valid")
	private String email;
	
	@NotBlank(message = "locality cannot be blank")
	private String locality;
	
	@NotBlank(message = "city cannot be blank")
	private String city;
	
	@NotBlank(message = "state cannot be blank")
	private String state;
	
	@NotBlank(message = "pincode cannot be blank")
	private String pinCode;
}
