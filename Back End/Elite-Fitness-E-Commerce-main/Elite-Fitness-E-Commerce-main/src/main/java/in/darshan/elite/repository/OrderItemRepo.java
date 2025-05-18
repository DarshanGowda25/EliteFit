package in.darshan.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.DTO.AdminOrderItemDTO;
import in.darshan.elite.DTO.ProductQuantityDTO;
import in.darshan.elite.entity.OrderItem;
import in.darshan.elite.entity.Product;
import in.darshan.elite.response.AdminOrders;

import java.util.List;


@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

	@Query("SELECT NEW in.darshan.elite.DTO.ProductQuantityDTO(oi.product, oi.quantity,oi.createdAt,oi.paymentMode,oi.rating) " +
		       "FROM OrderItem oi WHERE oi.order.orderId = :id")
	List<ProductQuantityDTO> getProduct(@Param("id") Integer id);

	@Modifying
	@Query("UPDATE OrderItem oi set oi.rating = :rating where oi.product.productId = :productId")
	void updateRating(@Param("productId") Integer productId,@Param("rating") Double rating);

	boolean existsByProduct_ProductId(Integer productId);

	
	@Query("SELECT NEW in.darshan.elite.DTO.AdminOrderItemDTO(oi.product, oi.quantity, oi.createdAt, oi.paymentMode, oi.price,oi.order) FROM OrderItem oi")
	List<AdminOrderItemDTO> getOrderItms();


}
