package in.darshan.elite.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import in.darshan.elite.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class MemberShipRequest {
	
	private String center;
	
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_Date;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_Date; 
    
    private Integer price;
    
    private String type;
    
   private Integer user_id;
   
   private String payment_status;

}
