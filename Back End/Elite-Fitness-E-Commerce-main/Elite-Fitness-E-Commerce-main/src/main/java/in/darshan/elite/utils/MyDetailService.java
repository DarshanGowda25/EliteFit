package in.darshan.elite.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.repository.UserRepo;


@Service
public class MyDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User u =repo.findByEmail(username);
		if(u==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}else {
			return new UserPrincipal(u);
		}
		
	}

}
