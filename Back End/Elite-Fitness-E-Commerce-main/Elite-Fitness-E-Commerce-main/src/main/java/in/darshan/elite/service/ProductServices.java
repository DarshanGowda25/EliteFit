package in.darshan.elite.service;

import java.util.List;

import org.springframework.data.domain.Page;

import in.darshan.elite.DTO.ProductNameCategoryDTO;
import in.darshan.elite.entity.Product;
import in.darshan.elite.request.productUpdateRequest;

public interface ProductServices {
	
	public Integer registerProduct(Product product);

	public boolean isProductExist(String name);

	public List<ProductNameCategoryDTO> getNameCategory(String filter);



	public Page<Product> getProducts(String category, Integer minPrice, Integer maxPrice, Integer rating, Integer page,
			Integer limit);

	public List<Product> getSimilarProduct(String category);

	public Product getProduct(Integer productId);

	public List<Product> getProducts(Integer[] cartProductIds);

	public List<Product> getTopSeller();

	public Integer getTotalOrderCount();

	public List<Product> getProductsInDemand();

	public Integer updateProduct(Product product);

	public void deleteProduct(Integer id);

}
