package in.darshan.elite.DTO;

import java.time.LocalDateTime;

import in.darshan.elite.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDTO {
	
	private Product product;
	private Integer quantity;
	private LocalDateTime orderDate;
	private String paymentMode;
	private Double rating;
	

}
