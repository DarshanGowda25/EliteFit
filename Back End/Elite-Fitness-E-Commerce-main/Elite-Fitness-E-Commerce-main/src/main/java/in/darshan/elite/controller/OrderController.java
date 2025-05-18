package in.darshan.elite.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.DTO.AdminOrderItemDTO;
import in.darshan.elite.DTO.CartProductDetails;
import in.darshan.elite.DTO.ProductQuantityDTO;
import in.darshan.elite.DTO.UserOrderDTO;
import in.darshan.elite.entity.Address;
import in.darshan.elite.entity.Cart;
import in.darshan.elite.entity.MemberShip;
import in.darshan.elite.entity.OrderItem;
import in.darshan.elite.entity.Orders;
import in.darshan.elite.entity.Product;
import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.AdminException;
import in.darshan.elite.exception.MemberShipException;
import in.darshan.elite.exception.OrderException;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.repository.OrderRepo;
import in.darshan.elite.request.MembershipUpdateRequest;
import in.darshan.elite.request.OrderRequest;
import in.darshan.elite.request.OrderUpdate;
import in.darshan.elite.request.RatingSubmitRequest;
import in.darshan.elite.response.Addressdetails;
import in.darshan.elite.response.AdminOrders;
import in.darshan.elite.response.UserOrderResponse;
import in.darshan.elite.service.CartService;
import in.darshan.elite.service.OrderServices;
import in.darshan.elite.service.ProductServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductServices productServices;
	
	@Autowired
	private OrderServices orderServices;
	
	
	@PostMapping("/order")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody OrderRequest request){
		
		final Integer convenienceFee = 29;
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
		if(request.getPaymentMode() == null || request.getAddressId() == null || request.getCartProductDetails() == null ) {

			throw new OrderException("Invalid Data");
		}
		System.out.println(request.getAddressId());
		System.out.println(request.getCartProductDetails().get(0).getProductId());
		
		Address address = cartService.getOrderAddress(request.getAddressId());
		User user = userServices.getUser(userDetails.getUserId());
		
		int totalMRP = 0;
		int discountAmount = 0;
		for (CartProductDetails cartDetails : request.getCartProductDetails()) {
		    Product product = productServices.getProduct(cartDetails.getProductId());
		    int quantity = cartDetails.getQuantity();
		    int price = product.getPrice();
		    int discountPerItem = price * product.getDiscount() / 100;

		    totalMRP += price * quantity;
		    discountAmount += discountPerItem * quantity;
		}

		int totalAmount = (totalMRP - discountAmount) + convenienceFee ;
		Orders order = new Orders();
		order.setCreatedAt(LocalDateTime.now());
		order.setTotalMRP(totalMRP);
		order.setDiscountAmount(discountAmount);
		order.setTotalAmount(totalAmount);
		int totalItem = request.getCartProductDetails().stream()
			    .mapToInt(CartProductDetails::getQuantity) // Get the quantity of each product
			    .sum(); // Sum the quantities
		order.setTotalItem(totalItem);
		order.setUser(user); 
		order.setAddress(address);
		order.setOrderStatus("Pending"); 
			
		Map<String, String> response = new HashMap<>();	
		List<OrderItem>  orderItems =   request.getCartProductDetails().stream().map(
				    cart -> {
				        Product product = productServices.getProduct(cart.getProductId());
				    	OrderItem orderItem = new OrderItem();
				    	orderItem.setQuantity(cart.getQuantity());
				    	int discountPrice = product.getPrice() - (product.getPrice() * product.getDiscount()/100);
				    	orderItem.setPrice(discountPrice);
						orderItem.setProduct(product);
						orderItem.setCreatedAt(LocalDateTime.now());
						orderItem.setPaymentMode(request.getPaymentMode());
						orderItem.setRating(0.0);
						
						return orderItem;
		
				    }).collect(Collectors.toList());
		Integer id = orderServices.placeOrder(order,orderItems);
		System.out.println(id);
		
		if(id>0) {
			boolean isCartClear = "cart".equals(request.getSource()) ? cartService.deleteCartItems() : true;

			if(isCartClear) {
				response.put("status", "Order placed successfully");
			}
				
		}else {
			throw new OrderException("Somthing Wrong! try later");
		}

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("getOrders")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getOrders(@AuthenticationPrincipal UserPrincipal userDetails) {
	    if (userDetails == null) {
	        throw new UserException("Unauthorized");
	    }

	    List<UserOrderDTO> orders = orderServices.getOrderIdStatus(userDetails.getUserId());

	    if (orders == null || orders.isEmpty()) {
	        throw new OrderException("No orders found for user.");
	    }

	    List<UserOrderResponse> orderDetails = orders.stream()
	        .flatMap(order ->
	            orderServices.getProduct(order.getId()).stream().map(item -> {
	                UserOrderResponse detail = new UserOrderResponse();
	                detail.setOrderId(order.getId()); // Optional: include order ID
	                detail.setOrderStatus(order.getStatus());
	                detail.setOrderDate(item.getOrderDate() != null ? item.getOrderDate().toLocalDate() : null);
	                detail.setQuantity(item.getQuantity());
	                detail.setPaymentMode(item.getPaymentMode());
	                detail.setProduct(item.getProduct());
	                detail.setRating(item.getRating());
	                return detail;
	            })
	        )
	        .collect(Collectors.toList());

	    if (orderDetails.isEmpty()) {
	        throw new OrderException("Server Error");
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("orders", orderDetails);
	    return ResponseEntity.ok(response);
	}

	
	
	@PatchMapping("rating")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> submitRating(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody RatingSubmitRequest request) {
	    if (userDetails == null) {
	        throw new UserException("Unauthorized");
	    }

	    boolean res = orderServices.updateRating(request.getProductId(),request.getRating());
	    Map<String,String> response = new HashMap<>();
	    if(!res) {
	    	throw new OrderException("Somthing wrong! try again later");
	    }
	    response.put("status", "Rating Submitted");
	    return ResponseEntity.ok(response); 
	}
	
	
	@GetMapping("getAllOrders")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllOrders(@AuthenticationPrincipal UserPrincipal userDetails){
		if(userDetails == null) {
			throw new UserException("Unauthorized");
		}
		
			List<AdminOrderItemDTO> orderItems = orderServices.getOrderItems();
			
			
			if(orderItems == null || orderItems.isEmpty()) {
				throw new OrderException("Server Error");
			}
			
		List<AdminOrders> orderDetails = orderItems.stream().map(
				order ->{
					AdminOrders o = new AdminOrders();
					o.setOrderId(order.getOrders().getOrderId());
					o.setProductName(order.getProduct().getName());
					o.setPrice(order.getPrice());
					o.setQuantity(order.getQuantity());
					o.setOrderDate(order.getOrderDate().toLocalDate());
					o.setOrderStatus(order.getOrders().getOrderStatus());
					Addressdetails addressdetails  =  new Addressdetails();
					addressdetails.setName(order.getOrders().getAddress().getName());
					addressdetails.setPhone(order.getOrders().getAddress().getPhone());
					String address = order.getOrders().getAddress().getAddress() +" -"+order.getOrders().getAddress().getPincode();
					addressdetails.setAddress(address);
					o.setAddress(addressdetails);
					o.setPaymentMode(order.getPaymentMode());
					return o;
					
				}
				).collect(Collectors.toList());
		
		Map<String,Object> response = new HashMap<>();
		response.put("orders", orderDetails);
		return ResponseEntity.ok(response); 
	}
	
	@PatchMapping("update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStatus(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody OrderUpdate request){
		if(userDetails == null) {
			throw new AdminException("Unautherized");
		}
		if(request.getId() == null || request.getStatus()==null) {
			throw new AdminException("Invalid Request");
		}
		Orders order =  orderServices.getOrder(request.getId());
		if(order == null) {
			throw new MemberShipException("Inavlid Order Update Request");
		}
		order.setOrderStatus(request.getStatus());
		orderServices.updateStatus(order);;
		return ResponseEntity.ok("updated successfully");
		
	}
	

}
