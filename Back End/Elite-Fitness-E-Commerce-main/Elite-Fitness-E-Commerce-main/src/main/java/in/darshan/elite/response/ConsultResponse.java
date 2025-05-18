package in.darshan.elite.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultResponse {
	
	private Integer consult_id;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String consult_type;
	private Date date; 
	


}
