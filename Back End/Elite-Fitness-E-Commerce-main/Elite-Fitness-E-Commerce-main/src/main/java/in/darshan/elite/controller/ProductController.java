package in.darshan.elite.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.DTO.ProductNameCategoryDTO;
import in.darshan.elite.entity.Product;
import in.darshan.elite.entity.User;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.AdminException;
import in.darshan.elite.exception.ProductException;
import in.darshan.elite.exception.UserException;
import in.darshan.elite.request.ProductAddRequest;
import in.darshan.elite.request.productUpdateRequest;
import in.darshan.elite.service.ProductServices;
import in.darshan.elite.service.UserServices;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired 
	private UserServices userServices;
	
	
	@Autowired
	private ProductServices services;
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody ProductAddRequest request){
		
		if(userDetails == null) {
			throw new UserException("Unathorized");
		}
		
		if(request.getName()==null || request.getCategory()==null || request.getDiscount()==null ||
				request.getImage()==null || request.getPrice() == null || request.getDescription()==null) {
			
			throw new ProductException("Invalid Data");
		}
		
		boolean isExist = services.isProductExist(request.getName());
		if(isExist) {
			throw new ProductException("Product with the same name already exists");
		}
		 
		
		Product p = new Product();
		p.setAvailable(1);
		
		String category = request.getCategory().replace(" ", "");
		p.setCategory(category.toLowerCase());
		p.setDiscount(request.getDiscount());
		p.setImage(request.getImage());
		p.setName(request.getName());
		p.setPrice(request.getPrice());
		p.setDescription(request.getDescription());
		p.setBuyCounts(0);
		p.setRating((double) 0);

		
		Integer id = services.registerProduct(p);
		Map<String, String> response = new HashMap<>();
		if(id>0) {
			response.put("status", "Product Added Successfully");
		}
		else {
			throw new ProductException("Failed to add product");
		}
		
		 return ResponseEntity.ok(response);
		 
		
		
	}
	
	@GetMapping("/suggestions")
	public ResponseEntity<?> getSuggestion(@RequestParam(required = false) String filter){
		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(filter);
		List<ProductNameCategoryDTO> nameCategorys = services.getNameCategory(filter.toLowerCase());
		
		
		

		Set<String> suggestions = new HashSet<String>();
		
		for (ProductNameCategoryDTO nameCategory : nameCategorys) {
			suggestions.add(nameCategory.getCategory());
			suggestions.add(nameCategory.getName());
			
		}
		
		return ResponseEntity.ok(suggestions);
		
	}
	
	
	@GetMapping("getAll")
	public ResponseEntity<?> getProducts(
	        @RequestParam(required = false, defaultValue = "") String category,
	        @RequestParam(required = false, defaultValue = "0-infinity") String price,
	        @RequestParam(required = false, defaultValue = "0") Integer rating,
	        @RequestParam(required = false, defaultValue = "1") Integer page,
	        @RequestParam Integer limit) {
	    
		page = page-1;
	    System.out.println(price);
	    String[] minMaxPrice = price.split("-");
	    Integer minPrice = Integer.parseInt(minMaxPrice[0]);
	    Integer maxPrice = minMaxPrice[1].equals("infinity") ? Integer.MAX_VALUE : Integer.parseInt(minMaxPrice[1].trim());
	    System.out.println(minPrice);
	    System.out.println(maxPrice);

	    String Category = category.replace("-", " ").trim();
	    
	    Page<Product> data = services.getProducts(Category, minPrice, maxPrice, rating, page, limit);
	    
	    return ResponseEntity.ok(data);
	}
	
	@GetMapping("/similar")
	public ResponseEntity<?> getSimlarProducts(@RequestParam String category){
		
		List<Product> similarProducts = services.getSimilarProduct(category);
		return ResponseEntity.ok(similarProducts);
	}
	
	@GetMapping("/topSeller")
	public ResponseEntity<?> getTopSeller() {
		
		List<Product> topSeller = services.getTopSeller();
		return ResponseEntity.ok(topSeller);
	}
	
	
	@PatchMapping("/update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateProduct(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody productUpdateRequest request) {
		if(userDetails == null) {
			throw new AdminException("Unauthorized");
		}
		
		Product existingProduct = services.getProduct(request.getProductId());
		if(existingProduct == null) {
			throw new ProductException("Product Not Found");
		}
		
	    if(request.getName() == null && request.getDescription() == null && request.getAvailable() == null 
	    	       && request.getPrice() == null && request.getDiscount() == null) {
	    	        throw new ProductException("No fields provided to update");
	    }
	    
		if (request.getName() != null) {
		    existingProduct.setName(request.getName());
		}
		if (request.getDescription() != null) {
		    existingProduct.setDescription(request.getDescription());
		}
		if (request.getAvailable() != null) {
		    existingProduct.setAvailable(request.getAvailable());
		}
		if (request.getPrice() != null) {
		    existingProduct.setPrice(request.getPrice());
		}
		if (request.getDiscount() != null) {
		    existingProduct.setDiscount(request.getDiscount());
		}
		
		Integer id = services.updateProduct(existingProduct); 
		Map<String, String> response = new HashMap<>();
		
		if(id>0) {
			response.put("status", "Product Updated");
		}else {
			throw new ProductException("Product update failed");
		}
		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal UserPrincipal userDetails,@RequestParam Integer id) {
		System.out.println(id);
		if(userDetails == null) {
			throw new AdminException("Unautorized");
		}
		if(id == null) {
			throw new ProductException("Invalid Request");
		}
		services.deleteProduct(id);
		return ResponseEntity.ok("Product Deleted");
	}


	

}
