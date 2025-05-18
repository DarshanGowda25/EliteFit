package in.darshan.elite.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.entity.Consultancy;
import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.ConsultancyException;
import in.darshan.elite.exception.MemberShipException;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.ConsultRequest;
import in.darshan.elite.response.ConsultResponse;
import in.darshan.elite.service.ConsultancyServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/consultancy")
public class ConsultancyController {
	
	@Autowired
	private ConsultancyServices services;
	
	@Autowired
	private UserServices userServices;
	
	
	@PostMapping("/book")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> bookAppointment(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody ConsultRequest request){
		
		if(userDetails==null) {
			throw new UserException("Unathorized");
		}
		
		if(request.getUserId() ==null  || request.getAge()==null ||  request.getHeight()==null || request.getWeight()==null 
				|| request.getConsult_type() == null || request.getDate()==null) {
			
			throw new ConsultancyException("Invalid Data");
			
		}
		
		Consultancy c = new Consultancy();
		
		User u = userServices.getUser(request.getUserId());
	
		c.setAge(request.getAge());
		c.setHeight(request.getHeight());
		c.setWeight(request.getWeight());
		c.setDate(request.getDate());
		c.setConsult_type(request.getConsult_type());
		c.setUser(u);
		
		
		Integer id = services.book(c);
		
		Map<String,String> response = new HashMap<>();
		if(id>0) {
			response.put("status", "SuccessFully Booked");
		}
		else {
			throw new MemberShipException("Failed to book");
		}
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getConsultancies(@AuthenticationPrincipal UserPrincipal userDetails){
		if(userDetails==null) {
			throw new UserException("Unotherizwd");
		}
				List<ConsultResponse> consultancies = services.getConsultencies(userDetails.getUserId());
				if(consultancies==null) {
					throw new ConsultancyException("Failed to fetch data");
				}
				
				return ResponseEntity.ok(consultancies);
				
		
	}

}
