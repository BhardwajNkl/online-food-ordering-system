package dev.bhardwaj.food_order.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private float price;
	private float averageRating;
	private boolean avaialability;
	private Cuisine cuisine;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "dish")
	private List<Order> orders;
	
	@OneToMany
	private List<Rating> ratings;
	
	@OneToMany
	private List<Review> reveiws;
	
	private enum Cuisine{
		CHINESE,
		CONTINENTAL,
		NORTH_INDIAN,
		SOUTH_INDIAN
	}
}
