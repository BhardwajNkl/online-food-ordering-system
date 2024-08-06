package dev.bhardwaj.food_order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewCustomerDto {
	@NotBlank(message = "name canot be blank")
	private String name;
	
	@Email(message = "email is not valid")
	@NotNull(message = "email cannot be null")
	private String email;
	
	@NotBlank(message = "password cannot be blank")
	@Size(min = 6, max = 12, message = "password must have at least 6 and at most 12 characters")
	private String password;
	
	@NotBlank(message = "locality cannot be blank")
	private String locality;
	
	@NotBlank(message = "city cannot be blank")
	private String city;
	
	@NotBlank(message = "state cannot be blank")
	private String state;
	
	@NotBlank(message = "pincode cannot be blank")
	private String pinCode;
}
