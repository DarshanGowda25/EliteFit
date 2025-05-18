package in.darshan.elite.request;

import java.util.List;

import in.darshan.elite.DTO.CartProductDetails;
import lombok.Data;

@Data
public class OrderRequest {

	private String source;
	private String paymentMode;
	private Integer addressId;
	private List<CartProductDetails> cartProductDetails;
}
