package in.darshan.elite.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler  {
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Map<String,String>> userExceptionHandler(UserException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "User Exception");
		response.put("message", e.getMessage());
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		 
	}
	
	
	
	@ExceptionHandler(MemberShipException.class)
	public ResponseEntity<Map<String,String>> userMemberShipExceptionHandler(MemberShipException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "MemberShip Exception");
		response.put("message", e.getMessage());
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
	}
	
	@ExceptionHandler(ConsultancyException.class)
	public ResponseEntity<Map<String,String>> userConsultancyExceptionHandler(ConsultancyException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "Consultancy Exception");
		response.put("message", e.getMessage()); 
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
	}
	
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<Map<String,String>> productExceptionHandler(ProductException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "Product Exception");
		response.put("message", e.getMessage()); 
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(CartException.class)
	public ResponseEntity<Map<String,String>> productExceptionHandler(CartException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "Cart Exception");
		response.put("message", e.getMessage()); 
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<Map<String,String>> OrderExceptionHandler(OrderException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "Order Exception");
		response.put("message", e.getMessage()); 
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				
	}
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<Map<String,String>> OrderExceptionHandler(AdminException e){
		Map<String,String> response = new HashMap<>();
		response.put("error", "Admin Exception");
		response.put("message", e.getMessage()); 
		response.put("status", "Failed");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				
	}


}
