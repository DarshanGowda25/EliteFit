package in.darshan.elite.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.darshan.elite.entity.User;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.repository.UserRepo;
import in.darshan.elite.request.UserSignInRequest;
import in.darshan.elite.service.UserServices;
import in.darshan.elite.utils.JWTService;




@Service
public class UserServicesImpl implements UserServices{
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder enCoder = new BCryptPasswordEncoder(12);

	@Override
	public Integer register(User user) {
		// TODO Auto-generated method stub
		user.setPassword(enCoder.encode(user.getPassword()));
		return repo.save(user).getUser_id(); 
	}
	
	public String login(UserSignInRequest user) {
		// TODO Auto-generated method stub
		
		String role = repo.getRole(user.getEmail());
		Authentication authentication =   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.genrerateToken(user.getEmail(),role);
		}
		else {
			throw new UserException("Authentication Failed");
			
		}
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public Integer update(User user) {
		// TODO Auto-generated method stub
		return repo.save(user).getUser_id();
	}
	
	public String getRole(String email) {
		return repo.getRole(email);
		
	}
	
	



}
