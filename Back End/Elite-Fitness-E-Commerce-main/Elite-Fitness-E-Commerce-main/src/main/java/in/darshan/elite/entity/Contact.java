package in.darshan.elite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contact_id;
	private String name;
	private String email;
	private Long phoneNo;
	private String message;
	private String status;
	

}
