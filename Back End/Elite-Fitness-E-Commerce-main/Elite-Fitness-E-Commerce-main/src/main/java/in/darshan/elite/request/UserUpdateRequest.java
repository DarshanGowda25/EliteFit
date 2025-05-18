package in.darshan.elite.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
	
	private Integer user_id;
	private String name;
	private String email;
	private String phone;
	
	

}
