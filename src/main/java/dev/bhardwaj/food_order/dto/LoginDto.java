package dev.bhardwaj.food_order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
	@NotBlank(message = "username/email cannot be blank")
	private String email;
	
	@NotBlank(message = "password cannot be blank")
	@Size(min = 6, max = 12, message = "password must have at least 6 and at most 12 characters")
	private String password;
}
