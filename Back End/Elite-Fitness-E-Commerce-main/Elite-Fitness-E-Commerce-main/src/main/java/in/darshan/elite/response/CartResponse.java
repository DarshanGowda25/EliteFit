package in.darshan.elite.response;

import in.darshan.elite.entity.Product;

import lombok.Data;

@Data
public class CartResponse {
	
	private Integer cartId;

	private Product product;
	
}
