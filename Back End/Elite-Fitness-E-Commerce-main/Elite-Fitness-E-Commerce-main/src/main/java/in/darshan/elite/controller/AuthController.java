package in.darshan.elite.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




import in.darshan.elite.entity.User;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.UserSignInRequest;
import in.darshan.elite.request.UserSignUpRequest;
import in.darshan.elite.service.UserServices;


@RestController
@RequestMapping("auth")
public class AuthController {
	
	@Autowired
	private UserServices services;
	
	private BCryptPasswordEncoder enCoder = new BCryptPasswordEncoder(12);
	
	
	@PostMapping("/userRegister")
	public ResponseEntity<?> userRegister(@RequestBody UserSignUpRequest user){
		
		String status = "";
		if(user.getName()==null || user.getEmail()==null || user.getPhone()==null || user.getGender()==null ||
				user.getDOB()==null || user.getAddress()==null || user.getPassword()==null) {
			System.out.println("Invalid Data");
			throw new UserException("Invalid Data");
		}
		
		User checkMail = services.getUser(user.getEmail());
		if(checkMail!=null) {
			System.out.println("Email Already Exists");
			throw new UserException("Email Already Exists"); 
		} 
		System.out.println(user.getDOB());
		
		User u = new User();
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setPhone(user.getPhone());
		u.setGender(user.getGender());
		u.setDOB(user.getDOB());
		u.setAddress(user.getAddress());
		u.setPassword(user.getPassword());
		u.setCreatedAt(LocalDateTime.now());
		u.setRole("USER");
		
		Integer id = services.register(u);
		Map<String, String> response = new HashMap<>();
		
		if(id>0) {
			status = "Successfully Registered";
		}
		else {
			status = "Failed To Register";
		}
		response.put("status",status);
		return ResponseEntity.ok(response);
		
	}
	
	
	
	@PostMapping("/userLogin")
	public ResponseEntity<?> login(@RequestBody UserSignInRequest user) {
		
		if(user.getEmail() == null || user.getPassword()==null) {
			throw new UserException("InValid data");
		}
		
		System.out.println(user.getEmail());
		User u = services.getUser(user.getEmail());
		if(u == null) {
			throw new UserException("EmailId Not Found");
		}
		
		if(!enCoder.matches(user.getPassword(),u.getPassword() )) {
			throw new UserException("Incorrect Password");
		}
		
		
		
		String res = services.login(user);
		Map<String, String> response = new HashMap<>();
		
		String token = res;
		String role = services.getRole(user.getEmail());
		
		
		response.put("status", "success");
		response.put("token",token);
		response.put("role", role);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/adminRegister")
	public ResponseEntity<?> adminRegister(@RequestBody UserSignUpRequest user, @RequestParam String secretKey) {
		
		
	    if (!secretKey.equals("MY_ADMIN_SECRET_Ravana2525")) {
	        throw new RuntimeException("Unauthorized");
	    }

	    User u = new User();
	    u.setName(user.getName());
	    u.setEmail(user.getEmail());
	    u.setPhone(user.getPhone());
	    u.setGender(user.getGender());
	    u.setDOB(user.getDOB());
	    u.setAddress(user.getAddress());
	    u.setPassword(user.getPassword());
	    u.setCreatedAt(LocalDateTime.now());
	    u.setRole("ADMIN");

	    Integer id = services.register(u);
	    Map<String,String> response = new HashMap<>();
	    if(id>0) {
	    	response.put("status", "Admin Registered");
	    }else {
	    	throw new UserException("Failed to register");
	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/addAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAdmin(@RequestBody UserSignUpRequest user, @RequestParam String secretKey) {
	    
	    if (!secretKey.equals("MY_ADMIN_SECRET")) {
	        throw new RuntimeException("Unauthorized");
	    }
	    
	    User existing = services.getUser(user.getEmail());
	    if (existing != null) {
	        throw new UserException("Email already exists");
	    }

	    User u = new User();
	    u.setName(user.getName());
	    u.setEmail(user.getEmail());
	    u.setPhone(user.getPhone());
	    u.setGender(user.getGender());
	    u.setDOB(user.getDOB());
	    u.setAddress(user.getAddress());
	    u.setPassword(user.getPassword()); 
	    u.setCreatedAt(LocalDateTime.now());
	    u.setRole("admin");

	    
	    Integer id = services.register(u);
	    Map<String,String> response = new HashMap<>();
	    if(id>0) {
	    	response.put("status", "Admin Registered");
	    }else {
	    	throw new UserException("Failed to register");
	    }

	    return ResponseEntity.ok(response);
	}


	
	
	



}
