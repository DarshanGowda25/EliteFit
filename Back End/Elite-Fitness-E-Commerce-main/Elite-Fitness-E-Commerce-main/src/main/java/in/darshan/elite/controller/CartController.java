package in.darshan.elite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.entity.Address;
import in.darshan.elite.entity.Cart;
import in.darshan.elite.entity.Product;
import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.CartException;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.addAddressRequest;
import in.darshan.elite.request.cartAddRequest;
import in.darshan.elite.response.AddressResponse;
import in.darshan.elite.response.CartResponse;
import in.darshan.elite.service.CartService;
import in.darshan.elite.service.ProductServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private final Integer CART_LIMIT = 20;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ProductServices productServices;
	
	@Autowired
	private CartService cartService;
	

	
	

	@PostMapping("/add")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addToCart(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody cartAddRequest request){
		System.out.println(userDetails.getUserId());
		System.out.println(request.getUserId());
		System.out.println(request.getProductId());
		
		if( !userDetails.getUserId().equals(request.getUserId())) {
			throw new UserException("Unathoraized");
		}
		 
		Map<String, String> response = new HashMap<>();
		if(request.getUserId() != null && request.getProductId() != null) {
			
			User user = userServices.getUser(request.getUserId());
			Product product = productServices.getProduct(request.getProductId());
			
			if(user == null || product == null) {
				throw new CartException("Failed to add to cart");
			}
			
			if(cartService.getCartCount() > CART_LIMIT ) {
				throw new CartException("Maximum Cart Limit Reached");
			}
			
			if(cartService.isPresent(request.getProductId() , request.getUserId())) {
				throw new CartException("Product Exist In Cart");
			}
			
			Cart cart = new Cart();
			cart.setProduct(product);
			cart.setUser(user);
			
			Integer id = cartService.addToCart(cart);
			
			
			if(id>0) {
				response.put("status", "Product Added To cart");
			}else {
				throw new CartException("Failed to add product to cart");
			}
			
			
		}
		
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/cartDetails")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCartItems(@AuthenticationPrincipal UserPrincipal userDetails){
		
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		 List<CartResponse> cartDetails = cartService.getCart(userDetails.getUserId()).stream().map(
				 c -> {
					CartResponse cr = new CartResponse();
					cr.setCartId(c.getCartId());
					cr.setProduct(c.getProduct());
					return cr;
				 }).collect(Collectors.toList());
		 
		 Map<String, List<CartResponse>> response = new HashMap<>();
		 
		 if(cartDetails != null) {
			 response.put("cart", cartDetails);
		 }
		 else {
			 throw new CartException("Failed to get Cart Details");
		 }
		 
		 return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("remove")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> removeProduvt(@AuthenticationPrincipal UserPrincipal userDetails,@RequestParam Integer id){
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		Boolean isRemoved = cartService.removeProduct(id);
		Map<String, String> response = new HashMap<>();
		if(isRemoved) {
			response.put("status", "Product removed from cart");
		}
		else {
			response.put("status", "Failed to remove product");
		}
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/addAddress")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addAddress(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody addAddressRequest request){
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		System.out.println(request.getAddresstype());
		System.out.println(request.getName());
		if(request.getName() == null || request.getEmail() == null || request.getPhone() == null || request.getAddress() == null ||
				request.getTown() == null || request.getPincode() == null || request.getDistrict() == null || request.getState() == null
				|| request.getAddresstype() == null) {
			throw new CartException("Invalid Address");
		}
		
		Address address = new Address();
		address.setName(request.getName());
		address.setEmail(request.getEmail());
		address.setPhone(Long.parseLong(request.getPhone()));
		address.setAddress(request.getAddress());
		address.setTown(request.getTown());
		address.setPincode(Integer.parseInt(request.getPincode()));
		address.setDistrict(request.getDistrict());
		address.setState(request.getState());
		address.setAddresstype(request.getAddresstype());
		User user  = userServices.getUser(userDetails.getUserId());
		address.setUser(user);
		
		Integer id = cartService.addAddress(address);
		Map<String,String> response = new HashMap<>();
		if(id>0) {
			response.put("status", "Address Saved");
		}else {
			throw new CartException("Failed to add address");
		}
			
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/addressDetails")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAddress(@AuthenticationPrincipal UserPrincipal userDetails){
		
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		List<AddressResponse> addressResponse = cartService.getAddress(userDetails.getUserId()).stream().map(
				a -> {
					AddressResponse address = new AddressResponse();
					address.setAddressId(a.getAddressId());
					address.setName(a.getName());
					address.setEmail(a.getEmail());
					address.setPhone(a.getPhone());
					address.setAddress(a.getAddress());
					address.setTown(a.getTown());
					address.setPincode(a.getPincode());
					address.setDistrict(a.getDistrict());
					address.setState(a.getState());
					address.setAddressType(a.getAddresstype());
					return address;
				}).collect(Collectors.toList());
		
		Map<String, List<AddressResponse>> response = new HashMap<>();
		
		if(addressResponse != null) {
			response.put("address", addressResponse);
		}else {
			throw new CartException("Failed to add address");
		}
		 return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("removeAddress")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> removeAddress(@AuthenticationPrincipal UserPrincipal userDetails,@RequestParam Integer id){
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		Boolean isRemoved = cartService.removeAddress(id);
		Map<String, String> response = new HashMap<>();
		if(isRemoved) {
			response.put("status", "Address removed ");
		}
		else {
			response.put("status", "Failed to remove address");
		}
		return ResponseEntity.ok(response);
	}
	




	


}

