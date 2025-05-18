package in.darshan.elite.service;

import org.springframework.data.jpa.repository.JpaRepository;

import in.darshan.elite.entity.User;
import in.darshan.elite.request.UserSignInRequest;

public interface UserServices{
	
	public Integer register(User user);
	public String login(UserSignInRequest user);
	public User getUser(String email);
	public User getUser(Integer id);
	public Integer update(User user);
	public String getRole(String email);

}
