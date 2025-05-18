package in.darshan.elite.request;

import lombok.Data;

@Data
public class UserPasswordResetRequest {

	private String email;
	private String oldPassword;
	private String newPassword;
	private String conPassword;
}
