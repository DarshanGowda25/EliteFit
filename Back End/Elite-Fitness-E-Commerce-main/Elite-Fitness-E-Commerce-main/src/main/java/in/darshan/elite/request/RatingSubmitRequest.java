package in.darshan.elite.request;

import lombok.Data;

@Data
public class RatingSubmitRequest {
	
	private Integer productId;
	private Double rating;

}
