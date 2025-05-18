package in.darshan.elite.request;

import lombok.Data;

@Data
public class productUpdateRequest {
	
	
	private Integer productId;
	private String name;
	private Integer available;
	private String description;
	private Integer price;
	private Integer discount;

}
