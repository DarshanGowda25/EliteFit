package in.darshan.elite.service;

import java.util.List;

import in.darshan.elite.DTO.AdminOrderItemDTO;
import in.darshan.elite.DTO.ProductQuantityDTO;
import in.darshan.elite.DTO.UserOrderDTO;
import in.darshan.elite.entity.MemberShip;
import in.darshan.elite.entity.OrderItem;
import in.darshan.elite.entity.Orders;
import in.darshan.elite.entity.Product;
import in.darshan.elite.response.AdminOrders;

public interface OrderServices {



	public Integer placeOrder(Orders order, List<OrderItem> orderItems);



	public List<ProductQuantityDTO> getProduct(Integer id);

	public boolean updateRating(Integer productId, Double rating);

	public Integer getTodaySales();

	

	public List<AdminOrderItemDTO> getOrderItems();

	public Orders getOrder(Integer id);

	public void updateStatus(Orders order);

	public List<UserOrderDTO> getOrderIdStatus(Integer userId);

}
