package in.darshan.elite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.DTO.UserNameMembershipDTO;
import in.darshan.elite.entity.Product;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.AdminException;
import in.darshan.elite.repository.ContactRepository;
import in.darshan.elite.service.MemberShipServices;
import in.darshan.elite.service.OrderServices;
import in.darshan.elite.service.ProductServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private ProductServices productServices;
	
	@Autowired
	private OrderServices orderServices;
	
	@Autowired
	private MemberShipServices memberShipServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ContactRepository repo;
	
	@GetMapping("/getDashData")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getDashData(@AuthenticationPrincipal UserPrincipal userDetails){
		if(userDetails == null) {
			throw new AdminException("Unautjerized");
		}
		
		Integer todaySales = orderServices.getTodaySales();
		Integer totalSales = productServices.getTotalOrderCount();
		Integer totalMemberships = memberShipServices.getMemberShipCount();
		Integer queryCount = repo.getUnanswered();
		List<Product> products = productServices.getProductsInDemand();
		PageRequest pageRequest = PageRequest.of(0, 6);
		List<UserNameMembershipDTO> membershipBuyers = memberShipServices.getMembershipsDashData(pageRequest);
		
		if(todaySales == null || totalSales ==  null || totalMemberships == null || queryCount == null || products == null || membershipBuyers == null) {
			throw new AdminException("Invalid reponse data");
		}
		
		Map<String, Object> response = Map.of(
		        "todaySales", todaySales,
		        "totalSales", totalSales,
		        "totalMemberships", totalMemberships,
		        "queryCount", queryCount,
		        "products",products,
		        "MemberShipBuyers", membershipBuyers
		    );
		return ResponseEntity.ok(response);
	}
	
	
	

}
