package in.darshan.elite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.entity.Cart;
import in.darshan.elite.response.CartResponse;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

	
	@Query("SELECT COUNT(c) > 0 FROM Cart c WHERE c.product.id = :productId AND c.user.id = :userId")
	boolean existsByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);
	
	@Query("SELECT c FROM Cart c WHERE c.user.user_id = :id")
	List<Cart> getCartDetails(@Param("id") Integer id);

	List<Cart> findByCartIdIn(Integer[] cartIds);

}
 