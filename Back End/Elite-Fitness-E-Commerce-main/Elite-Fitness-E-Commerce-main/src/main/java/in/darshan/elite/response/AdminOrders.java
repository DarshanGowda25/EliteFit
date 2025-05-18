package in.darshan.elite.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import in.darshan.elite.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrders {
	
	private Integer orderId;
	private String  productName;
	private Integer price;
	private Integer quantity;
	private LocalDate orderDate;
	private String paymentMode;
	private String orderStatus;
	private Addressdetails Address;

}
