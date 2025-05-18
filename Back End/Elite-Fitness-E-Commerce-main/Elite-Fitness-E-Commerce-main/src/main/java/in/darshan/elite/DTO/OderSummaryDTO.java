package in.darshan.elite.DTO;

import lombok.Data;

@Data
public class OderSummaryDTO {

	private Integer price;
	private Integer discountAmount;
	private	Integer totalAmount;
	private Integer totalItems;
	private Integer convenienceFee;
}
