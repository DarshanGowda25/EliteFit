package in.darshan.elite.controller;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.entity.Contact;
import in.darshan.elite.entity.MemberShip;
import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.AdminException;
import in.darshan.elite.exception.MemberShipException;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.MemberShipRequest;
import in.darshan.elite.request.MembershipUpdateRequest;
import in.darshan.elite.request.contactUpdateRequest;
import in.darshan.elite.response.MemberShipResponse;
import in.darshan.elite.response.MembershipsAdminResponse;
import in.darshan.elite.service.MemberShipServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/membership")
public class MemberShipController {
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private MemberShipServices services;
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> register(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody MemberShipRequest request){
		System.out.println();
		
		if((userDetails.getId())!=request.getUser_id()) {
			throw new UserException("Unauthorized");
		}
		
		if(request.getUser_id() == null || request.getType() == null || request.getPrice()==null || request.getPayment_status()==null ||
				request.getStart_Date()==null || request.getEnd_Date()==null) {
			throw new MemberShipException("Inavlid Data");
			
		}
		
		System.out.println(request.getUser_id());
		LocalDate validTill = services.vaildTill(request.getUser_id());
		
		if (services.iscenterPresent(request.getCenter()) && validTill != null && validTill.isAfter(request.getStart_Date())) {
		    throw new MemberShipException("Old membership valid till " + validTill);
		}
	
		
		User u = userServices.getUser(request.getUser_id());
		
		MemberShip memberShip = new MemberShip();
		
		memberShip.setCenter(request.getCenter());
		memberShip.setStart_Date(request.getStart_Date());
		memberShip.setEnd_Date(request.getEnd_Date());
		memberShip.setPrice(request.getPrice());
		memberShip.setType(request.getType());
		memberShip.setUser(u);
		memberShip.setPayment_status(request.getPayment_status());
		memberShip.setCreatedAt(LocalDateTime.now());
		
		
		Integer id = services.register(memberShip);
		
		Map<String,String> response = new  HashMap<>();
	

		if (id > 0) {
		    response.put("status", "Membership Bought");
		} else {
		    throw new MemberShipException("Failed to buy membership");
		}

		
		return ResponseEntity.ok(response);
		
		
	}
	
	@GetMapping("/getMemberships")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getMemberships(@AuthenticationPrincipal UserPrincipal userDetails){
		
		if(userDetails==null) {
			throw new UserException("Unauthorized");
		}
	
		
		
		List<MemberShipResponse> memberships = services.getMemberships(userDetails.getUserId());
		return ResponseEntity.ok(memberships);
		
		
	}
	
	@GetMapping("/getAllMemberships")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllMemberships(@AuthenticationPrincipal UserPrincipal userDetails){
		
		if(userDetails==null) {
			throw new UserException("Unauthorized");
		}
		
		
	
		List<MembershipsAdminResponse> memberships = services.getAllMemberships().stream().map(
				memberShip -> {
					MembershipsAdminResponse response = new MembershipsAdminResponse();
					response.setId(memberShip.getMemberShip_id());
					response.setName(memberShip.getUser().getName());
					response.setCenter(memberShip.getCenter());
					response.setStartDate(memberShip.getStart_Date());
					response.setEndDate(memberShip.getEnd_Date());
					response.setMembershipType(memberShip.getType());
					response.setPaymentStatus(memberShip.getPayment_status());
					return response;
				}).collect(Collectors.toList());
		
		return ResponseEntity.ok(memberships);
		
		
	}
	
	@PatchMapping("update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStatus(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody MembershipUpdateRequest request){
		if(userDetails == null) {
			throw new AdminException("Unautherized");
		}
		if(request.getId() == null || request.getStatus()==null) {
			throw new AdminException("Invalid Request");
		}
		MemberShip membership =  services.getMembership(request.getId());
		if(membership == null) {
			throw new MemberShipException("Inavlid Membership");
		}
		membership.setPayment_status(request.getStatus());
		services.updateStatus(membership);;
		return ResponseEntity.ok("updated successfully");
		
	}

}
