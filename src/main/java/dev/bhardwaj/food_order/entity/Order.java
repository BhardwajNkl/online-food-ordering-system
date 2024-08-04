package dev.bhardwaj.food_order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDateTime date;
	private float totalPrice;
	private String deliveryAddress;
	private DeliveryStatus deliveryStatus;
	
	@ManyToOne
	private Dish dish;
	
	@ManyToOne
	private Customer customer;
	
	public enum DeliveryStatus{
		ORDER_RECEIVED,
		ORDER_ON_THE_WAY,
		ORDER_DELIVERED
	}
	
	
}
