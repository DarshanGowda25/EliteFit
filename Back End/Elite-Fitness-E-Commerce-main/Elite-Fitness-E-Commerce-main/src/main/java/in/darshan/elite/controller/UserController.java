package in.darshan.elite.controller;



import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.UserPasswordResetRequest;
import in.darshan.elite.request.UserUpdateRequest;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserServices services;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userDetails){
		
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		User u = new User(); 
		User authenticatedUser = userDetails.getUser();
		u.setUser_id(authenticatedUser.getUser_id());
		u.setName(authenticatedUser.getName());
		u.setEmail(authenticatedUser.getEmail());
		u.setPhone(authenticatedUser.getPhone());
		u.setDOB(authenticatedUser.getDOB());
		u.setGender(authenticatedUser.getGender());
		return ResponseEntity.ok(u);
	}
	
	
	@PatchMapping("/update")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody UserUpdateRequest request) {
		
		if (!Objects.equals(userDetails.getId(), request.getUser_id())) {
		    throw new UserException("Unauthorized");
		}
		System.out.println(request.getUser_id());
		User u = services.getUser(request.getUser_id());

		u.setName(request.getName());
		u.setEmail(request.getEmail());
		u.setPhone(Long.parseLong(request.getPhone()));
		
		Map<String,String> response = new HashMap<>();

		Integer id = services.update(u);

		response.put("status", id > 0 ? "Successfully updated" : "Failed to update");
		return ResponseEntity.ok(response);
		
		
		
	}
	
	@PatchMapping("/reset")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> passwordRest(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody UserPasswordResetRequest request){
		
		if(!(userDetails.getEmail()).equals(request.getEmail())) {
			throw new UserException("Unauthorized");
		}
		
		User u = services.getUser(request.getEmail());
		if(!encoder.matches(request.getOldPassword(), u.getPassword())) {
			throw new UserException("Incorrect Old Password");
		}
		
		u.setPassword(encoder.encode(request.getNewPassword()));
		
		Map<String,String> response = new HashMap<>();
		Integer id = services.update(u);
		response.put("status", id > 0 ? "Reset Success" : "Failed To Reset");
		return ResponseEntity.ok(response);
		
		
	}

}
