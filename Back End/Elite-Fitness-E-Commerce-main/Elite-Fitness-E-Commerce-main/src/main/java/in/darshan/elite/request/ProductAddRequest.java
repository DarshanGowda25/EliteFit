package in.darshan.elite.request;

import lombok.Data;

@Data
public class ProductAddRequest {
	
	private String name;
	private String category;
	private String description;
	private Integer discount;
	private String image;
	private Integer price;

	

}
