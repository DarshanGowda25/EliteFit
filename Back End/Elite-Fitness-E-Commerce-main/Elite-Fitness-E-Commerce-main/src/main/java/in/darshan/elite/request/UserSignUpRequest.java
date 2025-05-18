package in.darshan.elite.request;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserSignUpRequest {
	
	
	private String name;

	private String email;

	private Long phone;
	
	private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate DOB; 
	
	private String address;
	
	private String password;
	
	

}
