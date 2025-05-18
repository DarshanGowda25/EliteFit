package in.darshan.elite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.DTO.ProductNameCategoryDTO;
import in.darshan.elite.DTO.ratingCountDTO;
import in.darshan.elite.entity.Product;
import in.darshan.elite.request.productUpdateRequest;

import java.util.List;

@Repository
public interface ProductRepo  extends JpaRepository<Product, Integer>{
	
 public	boolean existsByName(String name);
 
 @Query(value = "SELECT name, category FROM product WHERE name LIKE CONCAT('%', :filter, '%') OR category LIKE CONCAT('%', :filter, '%')", 
	       nativeQuery = true)
	public List<ProductNameCategoryDTO> getNameAndCategory(@Param("filter") String filter);


 	
 @Query("""
		    SELECT p FROM Product p 
		    WHERE 
		    (
		        (
		        :category IS NULL OR :category = '' OR 
		        LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')) OR 
		        LOWER(p.name) LIKE LOWER(CONCAT('%', :category, '%'))
		        )
		    )
		    AND (:minPrice IS NULL OR p.price >= :minPrice)
		    AND (:maxPrice IS NULL OR p.price <= :maxPrice)
		    AND (:rating IS NULL OR :rating = 0 OR p.rating >= :rating)
		    
		""")
		 Page<Product> getProducts(
		    @Param("category") String category,
		    @Param("minPrice") Integer minPrice,
		    @Param("maxPrice") Integer maxPrice,
		    @Param("rating") Integer rating, 
		    Pageable pageable
		);
 
 
 	public List<Product> findByCategory(String category);

 	List<Product> findByProductIdIn(Integer[] cartProductIds);

 	@Modifying
 	@Query("UPDATE Product p SET p.buyCounts = p.buyCounts + :quantity WHERE p.productId = :productId")
 	void updateBuyCount(@Param("productId") Integer productId,@Param("quantity") Integer quantity);
 	
	@Query(value = "select p.rating,p.buy_counts from product p where p.product_id = :productId", nativeQuery = true)
	ratingCountDTO getRating(@Param("productId") Integer productId);
	
	@Modifying
	@Query("UPDATE Product p set p.rating = :rating where p.productId = :productId")
	void updateRating(@Param("productId") Integer productId,@Param("rating") Double rating);

	 @Query(value = "select * from product order by buy_counts desc limit 6", nativeQuery = true)
	public List<Product> getTopSeller();

	@Query(value = "select sum(buy_counts) from product", nativeQuery = true)
	public Integer getTotalOrderCount();

	 @Query(value = "select * from product order by buy_counts desc limit 8", nativeQuery = true)
	public List<Product> getProductsInDemand();

	 


}

