package in.darshan.elite.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	
	private Integer totalItem;
	private Integer totalMRP;
	private Integer discountAmount;
	private Integer totalAmount;
	private LocalDateTime createdAt;
	private String orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "user_Id")
	private User user;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;
	
	@ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;
	    

	

}
