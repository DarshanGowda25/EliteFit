package in.darshan.elite.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer orderItemId;

	private Integer quantity;

	private Integer price;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Orders order;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	private LocalDateTime createdAt;
	private String paymentMode;
	private Double rating;
	

}
