package in.darshan.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.entity.Address;
import in.darshan.elite.response.AddressResponse;

import java.util.List;


@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{
	
	@Query("SELECT a from Address a where a.user.user_id = :id")
	public List<Address> getAddress(@Param("id") Integer id); 
	
	

}
