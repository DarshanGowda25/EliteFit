package in.darshan.elite.response;

import lombok.Data;

@Data
public class AddressResponse {
	private Integer addressId;
	private String name;
	private String email;
	private Long phone;
	private String address;
	private String town;
	private Integer pincode;
	private String district;
	private String state;
	private String addressType;

}
