package in.darshan.elite.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.DTO.UserNameMembershipDTO;
import in.darshan.elite.entity.MemberShip;
import in.darshan.elite.entity.User;
import in.darshan.elite.response.MemberShipResponse;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface MemberShipRepo extends JpaRepository<MemberShip, Integer>{
	
	public boolean existsByType(String type);
	public boolean existsByCenter(String center);
	
	




	
	@Query(value = "SELECT end_date FROM member_ship WHERE user_id = :id ORDER BY end_date DESC LIMIT 1", nativeQuery = true)
	public LocalDate getEndDate(@Param("id") Integer id);
	
	@Query(value = "SELECT member_ship_id,center,end_date,payment_status,price,start_date,type FROM member_ship WHERE user_id = :id", nativeQuery = true)
	public List<MemberShipResponse> findByUserId(@Param("id") Integer id);
	
	
	@Query("SELECT new in.darshan.elite.DTO.UserNameMembershipDTO(m.user.name, m.type) FROM MemberShip m ORDER BY m.createdAt DESC")
	List<UserNameMembershipDTO> getDashData(Pageable pageable);
	

	

	

}
