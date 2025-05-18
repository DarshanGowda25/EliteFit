package in.darshan.elite.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class MembershipsAdminResponse {
	
	private Integer id;
	private String name;
	private String membershipType;
	private String center;
	private LocalDate startDate;
	private LocalDate EndDate;
	private String paymentStatus;

}
