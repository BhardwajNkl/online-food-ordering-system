package dev.bhardwaj.food_order.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDto {
	private long orderId;
	private String customerName;
	private List<String> dishNames;
	private LocalDateTime date;
	private float totalPrice;
	private String deliveryAddress;
}
