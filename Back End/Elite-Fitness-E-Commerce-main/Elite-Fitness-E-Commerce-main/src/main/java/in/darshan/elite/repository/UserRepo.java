package in.darshan.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.darshan.elite.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	@Query("SELECT role from User where email =:email")
	public String getRole(@Param("email") String email);

}
