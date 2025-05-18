package in.darshan.elite.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.darshan.elite.DTO.AdminOrderItemDTO;
import in.darshan.elite.DTO.ProductQuantityDTO;
import in.darshan.elite.DTO.UserOrderDTO;
import in.darshan.elite.DTO.ratingCountDTO;
import in.darshan.elite.entity.OrderItem;
import in.darshan.elite.entity.Orders;
import in.darshan.elite.entity.Product;
import in.darshan.elite.repository.OrderItemRepo;
import in.darshan.elite.repository.OrderRepo;
import in.darshan.elite.repository.ProductRepo;
import in.darshan.elite.response.AdminOrders;
import in.darshan.elite.service.OrderServices;

@Service
public class OderServicesImpl implements OrderServices{
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	@Transactional
	public Integer placeOrder(Orders order, List<OrderItem> orderItems) {
	    Orders savedOrder = orderRepo.save(order); // Save once

	    for (OrderItem item : orderItems) {
	        item.setOrder(savedOrder);
	        productRepo.updateBuyCount(item.getProduct().getProductId(),item.getQuantity());
	        
	    }
	    orderItemRepo.saveAll(orderItems);
	    
	    

	    return savedOrder.getOrderId(); 
	}



	@Override
	public List<ProductQuantityDTO> getProduct(Integer id) {
		// TODO Auto-generated method stub
		return orderItemRepo.getProduct(id); 
	}

	@Override
	@Transactional
	public boolean updateRating(Integer productId, Double rating) {
		// TODO Auto-generated method stub
		orderItemRepo.updateRating(productId,rating);
		
		ratingCountDTO oldRating = productRepo.getRating(productId);
		

		double upadtedRating = Math.round((oldRating.getRating() * oldRating.getCount() + rating) / (oldRating.getCount() + 1));
		if(upadtedRating > 5) {
			upadtedRating = 4.5;
		}
		productRepo.updateRating(productId,upadtedRating);
		return true;
	}

	@Override
	public Integer getTodaySales() {
		// TODO Auto-generated method stub
		return orderRepo.getSalesCount();
	}

	@Override
	public List<AdminOrderItemDTO> getOrderItems() {
		// TODO Auto-generated method stub
		return orderItemRepo.getOrderItms();
	}

	@Override
	public Orders getOrder(Integer id) {
		// TODO Auto-generated method stub
		return orderRepo.findById(id).get();
	}

	@Override
	public void updateStatus(Orders order) {
		// TODO Auto-generated method stub
		orderRepo.save(order);
	}

	@Override
	public List<UserOrderDTO> getOrderIdStatus(Integer userId) {
		// TODO Auto-generated method stub
		return orderRepo.getOrderIdStatus(userId);
	}






}
