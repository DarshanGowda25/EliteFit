package in.darshan.elite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private Long phone;
	
	private String gender;
	
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate DOB; 
    
	private String address;
	
	private String password;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	private String role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<MemberShip> memberShips;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Consultancy> consultancies;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Cart> cartItems;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Address> addressList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
    private List<Orders> orders;
	 
	
	

	   
	  


}
