package in.darshan.elite.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Consultancy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer consult_id;
	
	private Integer age;
	private Integer height;
	private Integer weight;
	private String consult_type;
	private LocalDate date;

	
	 @ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	

}
