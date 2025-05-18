package in.darshan.elite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MemberShip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberShip_id;
	
	private String center;
	
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_Date;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_Date; 
    
    private Integer price;
    
    private String type;
    
    private String payment_status;
    
    @ManyToOne
	@JoinColumn(name = "user_Id")
	private User user;
    
    private LocalDateTime createdAt;
    
    

}
