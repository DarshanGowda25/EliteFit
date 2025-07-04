package in.darshan.elite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	
	@JoinColumn(name = "productId")
	@ManyToOne
	private Product product;
	
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

}
