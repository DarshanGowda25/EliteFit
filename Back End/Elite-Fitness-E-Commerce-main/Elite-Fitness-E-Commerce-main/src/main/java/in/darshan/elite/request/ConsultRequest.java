package in.darshan.elite.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConsultRequest {
	
	private Integer age;
	private Integer height;
	private Integer weight;
	private String consult_type;
	private LocalDate date;
	private Integer userId;
	

}
