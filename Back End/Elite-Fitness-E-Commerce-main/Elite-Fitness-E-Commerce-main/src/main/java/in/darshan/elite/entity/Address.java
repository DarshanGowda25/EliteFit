package in.darshan.elite.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;
	private String name;
	private String email;
	private Long phone;
	private String address;
	private String town;
	private Integer pincode;
	private String district;
	private String state;
	private String addresstype;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "address")
	private List<Orders> orders;
	

}
