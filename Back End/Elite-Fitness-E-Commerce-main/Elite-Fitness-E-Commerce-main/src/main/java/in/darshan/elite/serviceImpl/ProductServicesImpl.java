package in.darshan.elite.serviceImpl;


import java.awt.print.PrinterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.darshan.elite.DTO.ProductNameCategoryDTO;
import in.darshan.elite.entity.OrderItem;
import in.darshan.elite.entity.Product;
import in.darshan.elite.exception.ProductException;
import in.darshan.elite.repository.OrderItemRepo;
import in.darshan.elite.repository.OrderRepo;
import in.darshan.elite.repository.ProductRepo;
import in.darshan.elite.request.productUpdateRequest;
import in.darshan.elite.service.ProductServices;

@Service
public class ProductServicesImpl implements ProductServices{

	
	@Autowired
	public ProductRepo productRepo;
	
	@Autowired
	public OrderItemRepo orderItemRepo;
	
	@Override
	public Integer registerProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepo.save(product).getProductId();
	}

	@Override
	public boolean isProductExist(String name) {
		// TODO Auto-generated method stub
		return  productRepo.existsByName(name);
	}

	@Override
	public List<ProductNameCategoryDTO> getNameCategory(String filter) {
		// TODO Auto-generated method stub
		return productRepo.getNameAndCategory(filter);
	}



	@Override
	public Page<Product> getProducts(String category, Integer minPrice, Integer maxPrice, Integer rating, Integer page,
	    Integer limit) {
	    Pageable pageable = PageRequest.of(page, limit, Sort.by("rating").descending());
	    
	    // Pass the same value for both category and exactCategory
	    return productRepo.getProducts(category,minPrice,maxPrice,rating,pageable);
	}

	@Override
	public List<Product> getSimilarProduct(String category) {
		// TODO Auto-generated method stub
		return productRepo.findByCategory(category);
	}

	@Override
	public Product getProduct(Integer productId) {
		// TODO Auto-generated method stub
		return productRepo.findById(productId).get();
	}

	@Override
	public List<Product> getProducts(Integer[] cartProductIds) {
		// TODO Auto-generated method stub
		return productRepo.findByProductIdIn(cartProductIds);
	}

	@Override
	public List<Product> getTopSeller() {
		// TODO Auto-generated method stub
		return productRepo.getTopSeller();
	}

	@Override
	public Integer getTotalOrderCount() {
		// TODO Auto-generated method stub
		return productRepo.getTotalOrderCount();
	}

	@Override
	public List<Product> getProductsInDemand() {
		// TODO Auto-generated method stub
		return productRepo.getProductsInDemand();
	}

	@Override
	public Integer updateProduct(Product p) {
		// TODO Auto-generated method stub
		
		return productRepo.save(p).getProductId();
	}

	@Transactional
	@Override
	public void deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		boolean isPresent = productRepo.existsById(id);
		if(!isPresent) {
			throw new ProductException("Product Not Found");
		}
//		if (orderItemRepo.existsByProduct_ProductId(id)) {
//		    throw new ProductException("Cannot delete product. It's in use.");
//		}
		productRepo.deleteById(id);
		
	}




}
