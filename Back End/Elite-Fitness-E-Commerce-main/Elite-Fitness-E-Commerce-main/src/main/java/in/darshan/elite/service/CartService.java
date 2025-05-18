package in.darshan.elite.service;

import java.util.List;

import in.darshan.elite.entity.Address;
import in.darshan.elite.entity.Cart;
import in.darshan.elite.entity.Product;
import in.darshan.elite.response.AddressResponse;

public interface CartService {

  public Integer getCartCount();

public boolean isPresent(Integer productId, Integer userId);

public Integer addToCart(Cart cart);

public List<Cart> getCart(Integer userId);

public Boolean removeProduct(Integer id);

public Integer addAddress(Address address);

public List<Address> getAddress(Integer userId);

public Boolean removeAddress(Integer id);

public Address getOrderAddress(Integer id);

public List<Cart> getCartProducts(Integer[] cartProductIds);

public boolean deleteCartItems();


	
}
