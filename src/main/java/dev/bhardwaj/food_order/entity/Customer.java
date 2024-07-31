package dev.bhardwaj.food_order.entity;

import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	@Embedded
	private Address address;
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;
	
	@OneToMany
	private List<Rating> ratingsGiven;
	
	@OneToMany
	private List<Review> reviewsGiven;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	@Embeddable
	private class Address{
		private String locality;
		private String city;
		private String state;
		private String pinCode;
	}
}
