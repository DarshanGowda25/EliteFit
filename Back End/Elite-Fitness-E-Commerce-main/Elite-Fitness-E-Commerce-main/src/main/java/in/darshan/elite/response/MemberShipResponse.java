package in.darshan.elite.response;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MemberShipResponse {

	private Integer memberShip_id;
	private String center;
    private Date end_Date;
    private String payment_status;
    private Integer price;
    private Date start_Date;
    private String type;

}
