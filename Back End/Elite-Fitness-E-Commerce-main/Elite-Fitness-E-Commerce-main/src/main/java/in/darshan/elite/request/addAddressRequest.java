package in.darshan.elite.request;

import lombok.Data;

@Data
public class addAddressRequest {
	
	private String name;
	private String email;
	private String phone;
	private String address;
	private String town;
	private String pincode;
	private String district;
	private String state;
	private String addresstype;

}
