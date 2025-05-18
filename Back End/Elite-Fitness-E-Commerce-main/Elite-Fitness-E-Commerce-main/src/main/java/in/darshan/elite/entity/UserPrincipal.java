package in.darshan.elite.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
	
	private User user;
	
	
	

	public UserPrincipal(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	public User getUser() {
		return user;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return user.getUser_id();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public Integer getUserId() {
		return user.getUser_id();
	}


}
